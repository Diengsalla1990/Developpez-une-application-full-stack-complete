import { Injectable } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { BehaviorSubject, filter } from 'rxjs';

/**
* Service de gestion et de fourniture d'informations sur l'itinéraire actuel.
*/
@Injectable({
  providedIn: 'root',
})
export class RouteService {
  private currentRouteSubject: BehaviorSubject<string>;

  constructor(private router: Router) {
    this.currentRouteSubject = new BehaviorSubject<string>(
      this.getCurrentRoute()
    );

    // S'abonner aux événements du routeur pour mettre à jour la route actuelle
    this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd)) 
      .subscribe(() => {
        const currentRoute = this.getCurrentRoute();
        this.currentRouteSubject.next(currentRoute); 
      });
  }

  /**
   * @returns {string} route courent
   */
  getCurrentRoute(): string {
    return this.router.routerState.snapshot.url;
  }

  /**
* Obtient un observable de la route actuelle.
* @returns Un observable émettant la route actuelle.
*/
  getCurrentRoute$() {
    return this.currentRouteSubject.asObservable();
  }
}
