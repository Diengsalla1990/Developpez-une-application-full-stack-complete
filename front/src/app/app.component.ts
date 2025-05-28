import { Component, OnDestroy, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from "./component/header/header.component";
import { Subscription } from 'rxjs';
import { SessionService } from './core/session.service';
import { ThemeService } from './core/api/theme.service';
import { Theme } from './core/model/Theme.model';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit, OnDestroy {

  sessionLoginWithJwtIsLoading = true;
  sessionLoadingSubscription!: Subscription;
  sessionLoggedSubscription!: Subscription;

  constructor(
    private sessionService: SessionService,
    private themeService: ThemeService,
  ) {}
  
  ngOnInit(): void {
    // Attempt login with JWT stored in local storage (service will check if there is any and handle logic case success)
    this.sessionService.loginWithLocalStorageJwt();

    this.sessionLoadingSubscription = this.sessionService
      .$isLoading()
      .subscribe((isLoading: boolean) => {
        this.sessionLoginWithJwtIsLoading = isLoading;
      });

    this.sessionLoggedSubscription = this.sessionService
      .$isLogged()
      .subscribe((isLogged: boolean) => {
        // Fetch subjects only if user is logged in to avoid unauthorized access
        if (isLogged) {
          this.themeService.getAllSubjects().subscribe({
            next: (themes: Theme[]) => {
              this.themeService.themes = themes;
            },
            error: (error: any) => {
              console.log('error while fetching subjects');
            },
          });
        }
      });
  }

  ngOnDestroy(): void {
    if (this.sessionLoadingSubscription) {
      this.sessionLoadingSubscription.unsubscribe();
    }
    if (this.sessionLoggedSubscription) {
      this.sessionLoggedSubscription.unsubscribe();
    }
  }
}
