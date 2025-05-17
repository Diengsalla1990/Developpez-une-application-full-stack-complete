import { Component, OnDestroy, OnInit } from '@angular/core';
import { NavigationEnd, Router, RouterLink } from '@angular/router';
import { filter, Subscription } from 'rxjs';
import { RouteService } from '../../core/route.service';
import { SessionService } from '../../core/session.service';

@Component({
  selector: 'app-nav',
  imports: [RouterLink],
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.css'
})
export class NavComponent{

 isMeRouteActive = false;
 isDecoPRouteActive = false;
 isArticlesPRouteActive = false;
 isThemeRouteActive = false;

  constructor(private router: Router,private sessionService: SessionService) {
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: NavigationEnd) => {
        this.isThemeRouteActive = event.url.includes('/theme');
        this.isArticlesPRouteActive = event.url.includes('/articles');
        this.isMeRouteActive = event.url.includes('/me');
        // Ajoutez d'autres vérifications pour d'autres routes si nécessaire
      });
  }

  logout() {
    this.sessionService.logOut();
    this.router.navigateByUrl('/home');
  }


}
