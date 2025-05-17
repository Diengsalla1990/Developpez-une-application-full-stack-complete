import { Component, OnDestroy, OnInit } from '@angular/core';
import { NavComponent } from '../../component/nav/nav.component';
import { catchError, map, Observable, of, Subscription } from 'rxjs';
import { User } from '../../core/model/User.model';
import { Theme } from '../../core/model/Theme.model';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { SessionService } from '../../core/session.service';
import { ThemeService } from '../../core/api/theme.service';
import { Router } from '@angular/router';
import { UserService } from '../../core/api/user.service';
import { CommonModule } from '@angular/common';
import { UpdateUserRequest } from '../../core/api/interface/user/request/UpdateUserRequest';

@Component({
  selector: 'app-me',
  imports: [NavComponent,ReactiveFormsModule,
    CommonModule,],
  templateUrl: './me.component.html',
  styleUrl: './me.component.css'
})
export class MeComponent implements OnInit, OnDestroy{
  
  user$!: Observable<User | undefined>;
  userFetchError = false;
  themes$!: Observable<Theme[]>;
  themesFetchError = false;
  updateUserForm!: FormGroup;
  emailError = false;
  emailAlreadyTaken = false;
  usernameError = false;
  usernameAlreadyTaken = false;
  passwordError = false;
  updateUserError = false;
  isSubmitting = false;
  updateUserSuccess = false;
   themeId!: number;
   isError = false;
currentlyUnsubscribing: number | null = null;
  userServiceSubscription!: Subscription;

constructor(
    private sessionService: SessionService,
    private themeService: ThemeService,
    private router: Router,
    private userService: UserService,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.updateUserForm = this.formBuilder.group({
      email: [null, [Validators.required, Validators.email]],
      username: [null, Validators.required],
      password: [
        null,
        [
          Validators.pattern(
            /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/
          ),
        ],
      ],
    });

    this.user$ = this.sessionService.$getUser().pipe(
      map((user) => {
        this.userFetchError = false;
        return user;
      }),
      catchError((error: any) => {
        console.log(error);
        this.userFetchError = true;
        return of(undefined);
      })
    );

    // Remplir le formulaire de mise à jour de l'utilisateur avec les données utilisateur initiales
    this.user$.subscribe((user) => {
      if (user) {
        this.updateUserForm.patchValue({
          email: user.email,
          username: user.username,
        });
      }
    });

    this.themes$ = this.themeService.$getThemes();
  }

  ngOnDestroy(): void {
    if (this.userServiceSubscription) {
      this.userServiceSubscription.unsubscribe();
    }
  }

   /** Updates information Utilisateur */
  updateUser() {
    if (!this.isSubmitting) {
      this.isSubmitting = true;
      this.emailAlreadyTaken = false;
      this.emailError = false;
      this.usernameError = false;
      this.usernameAlreadyTaken = false;
      this.passwordError = false;
      this.updateUserError = false;
      this.updateUserSuccess = false;

      const emailFormControl = this.updateUserForm.controls['email'];
      const isEmailValid = emailFormControl.valid;
      const emailValue = emailFormControl.value;

      const usernameFormControl = this.updateUserForm.controls['username'];
      const isUsernameValid = usernameFormControl.valid;
      const usernameValue = usernameFormControl.value;

      const passwordFormControl = this.updateUserForm.controls['password'];
      const isPasswordValid = passwordFormControl.valid;
      const passwordValue = passwordFormControl.value;

      if (!isEmailValid) {
        this.emailError = true;
      }
      if (!isUsernameValid) {
        this.usernameError = true;
      }

      if (passwordValue !== null) {
        if (passwordValue.length > 0 && !isPasswordValid) {
          this.passwordError = true;
        }
      }

      // si la validation error est present, arrete soumission
      if (this.emailError || this.usernameError || this.passwordError) {
        this.isSubmitting = false;
        return;
      }

      const updateUserRequest: UpdateUserRequest = {
        email: emailValue,
        username: usernameValue,
        ...(passwordValue !== null &&
          passwordValue.length > 0 && { password: passwordValue }),
      };

      // Si les données utilisateur ne sont pas modifiées, arrêter la soumission
      if (
        this.sessionService.user?.email === updateUserRequest.email &&
        this.sessionService.user.username === updateUserRequest.username &&
        !updateUserRequest.password
      ) {
        this.isSubmitting = false;
        return;
      }

      // Envoyer une demande de mise à jour au serveur
      this.userServiceSubscription = this.userService
        .updateMe(updateUserRequest)
        .pipe(
          map((user: User) => {
            // Update session user data
            this.sessionService.updateUser(user);
           // Corriger les valeurs du formulaire avec les données utilisateur mises à jour
            this.updateUserForm.patchValue({
              email: user.email,
              username: user.username,
            });
            // Définir l'indicateur de réussite et réinitialiser l'état de soumission du formulaire
            this.updateUserSuccess = true;
            this.isSubmitting = false;
            //  Réinitialiser l'indicateur de réussite après 3 secondes
            setTimeout(() => {
              this.updateUserSuccess = false;
            }, 3000);
          }),
          catchError((error: any) => {
            switch (error.message) {
              case 'Email is already registered':
                this.emailAlreadyTaken = true;
                break;
              case 'Username is already registered':
                this.usernameAlreadyTaken = true;
                break;
              default:
                console.log('error', error);
                this.updateUserError = true;
            }
            this.isSubmitting = false;
            return of(null);
          })
        )
        .subscribe();
    }
  }

  /** Déconnecte l'utilisateur et accède à la page d'accueil */
  logout() {
    this.sessionService.logOut();
    this.router.navigateByUrl('/home');
  }

  unsubscribeFromSubject() {
    this.userService
      .unsubscribe(this.themeId)
      .pipe(
        // Réinitialise l'indicateur d'erreur et met à jour les données utilisateur en cas de désabonnement réussi
        map((user: User) => {
          this.isError = false;
          this.sessionService.updateUser(user);
        }),
        catchError((error) => {
          console.error(error);
          this.isError = true;
          return of(null);
        })
      )
      .subscribe();
  }

  
  unsubscribeFromTheme(themeId: number) {
  this.currentlyUnsubscribing = themeId;
  this.themeId = themeId;
  this.isError = false;

  this.userService
    .unsubscribe(themeId)
    .pipe(
      map((user: User) => {
        this.isError = false;
        this.sessionService.updateUser(user);
        this.currentlyUnsubscribing = null;
      }),
      catchError((error) => {
        console.error(error);
        this.isError = true;
        this.currentlyUnsubscribing = null;
        return of(null);
      })
    )
    .subscribe();
}


}
