import { Component, OnInit } from '@angular/core';
import { catchError, map, Observable, of } from 'rxjs';
import { Theme } from '../../core/model/Theme.model';
import { ThemeService } from '../../core/api/theme.service';
import { ThemeCard } from '../../interface/ThemeCard.interface';
import { CommonModule } from '@angular/common';
import { User } from '../../core/model/User.model';
import { SessionService } from '../../core/session.service';
import { UserService } from '../../core/api/user.service';
import { NavComponent } from '../../component/nav/nav.component';

@Component({
  selector: 'app-theme',
  imports: [CommonModule,NavComponent],
  templateUrl: './theme.component.html',
  styleUrl: './theme.component.css'
})
export class ThemeComponent implements OnInit{
 user$!: Observable<User | undefined>;
   themeId!: number;
   isError = false;
   unclickable = true;

   themeList$!: Observable<Theme[]>;
  themeFetchError = false;

  constructor(private themeService: ThemeService, private sessionService: SessionService,
      private userService: UserService) {}

  ngOnInit(): void {
    // Récupérer la liste des sujets et gérer les erreurs
    this.themeList$ = this.themeService.$getThemes().pipe(
      map((themes) => {
        this.themeFetchError = false;
        return themes;
      }),
      catchError((error: any) => {
        this.themeFetchError = true;
        return [];
      })
    );

     // Récupère les informations de l'utilisateur actuel
    this.user$ = this.sessionService.$getUser().pipe(
      map((user) => {
        this.isError = false;
        return user;
      }),
      catchError((error: any) => {
        console.error(error);
        this.isError = true;
        return of(undefined);
      })
    );
  }


  subscribeToSubject() {
      this.userService
        .subscribe(this.themeId)
        .pipe(
          // Réinitialise l'indicateur d'erreur et met à jour les données utilisateur en cas d'abonnement réussi
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

   
  
  unsubscribeFromSubject() {
    this.userService
      .unsubscribe(this.themeId)
      .pipe(
        //  Réinitialise l'indicateur d'erreur et met à jour les données utilisateur en cas de désabonnement réussi
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
}
