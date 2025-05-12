import { Component, OnDestroy, OnInit } from '@angular/core';
import { ButtonComponent } from '../../component/button/button.component';
import { Button } from '../../interface/Button.interface';
import { Router } from '@angular/router';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Subscription } from 'rxjs';
import { UserAuthService } from '../../core/api/user-auth.service';
import { SessionService } from '../../core/session.service';
import { LoginRequest } from '../../core/api/interface/userAuth/request/LoginRequest';

@Component({
  selector: 'app-login',
   standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit, OnDestroy {

  loginForm!: FormGroup;
  emailOrUsernameHasError = false;
  passwordHasError = false;
  credentialsError = false;
  credentialsErrorMessage = '';

  loginSubscription!: Subscription;
  sessionSubscription!: Subscription;

   constructor(
    private userAuthService: UserAuthService,
    private sessionService: SessionService,
    private router: Router,
    private formBuilder: FormBuilder,
  ) {}

   ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      emailOrUsername: [null, [Validators.required]],
      password: [null, [Validators.required]],
    });

   }

   /**
   * Handles form submission upon user login attempt.
   * Validates user inputs, performs login request, and handles response accordingly.
   */
  onSubmitForm(): void {
    // Reset error flags and messages
    this.emailOrUsernameHasError = false;
    this.passwordHasError = false;
    this.credentialsError = false;
    this.credentialsErrorMessage = '';

    const emailOrUsernameControl = this.loginForm.controls['emailOrUsername'];
    const isEmailOrUsernameValidInput = emailOrUsernameControl.valid;
    const emailOrUsernameValue = emailOrUsernameControl.value;

    const passwordControl = this.loginForm.controls['password'];
    const isPasswordValidInput = passwordControl.valid;
    const passwordValue = passwordControl.value;

    if (!isEmailOrUsernameValidInput) {
      this.emailOrUsernameHasError = true;
    }
    if (!isPasswordValidInput) {
      this.passwordHasError = true;
    }

    // If there are input errors, abort
    if (this.emailOrUsernameHasError || this.passwordHasError) {
      return;
    }

    const loginRequest: LoginRequest = {
      emailOrUsername: emailOrUsernameValue,
      password: passwordValue,
    };

    // Perform login request and handle response
    this.loginSubscription = this.userAuthService
      .login(loginRequest)
      .subscribe({
        next: (response: string) => {
          // Log in user and navigate to articles page
          this.sessionSubscription = this.sessionService
            .logIn(response)
            .subscribe((response: boolean) => {
              if (response) {
                this.router.navigateByUrl('/articles');
              } else {
                // Display credentials error message if login fails
                this.credentialsError = true;
                this.credentialsErrorMessage =
                  'Une erreur est produite, réessayez plus tard';
              }
            });
        },
        error: (err: any) => {
          this.credentialsError = true;
          if (err.status === 500) {
            this.credentialsErrorMessage = 'Une erreur est produite, réessayez plus tard';
          } else {
            this.credentialsErrorMessage = 'Informations identification non valides';
          }
        },
      });
  }


  ngOnDestroy(): void {
    if (this.loginSubscription) {
      this.loginSubscription.unsubscribe();
    }
    if (this.sessionSubscription) {
      this.sessionSubscription.unsubscribe();
    }
  }

   backToHome(): void {
    this.router.navigateByUrl('/home');
  }

}
