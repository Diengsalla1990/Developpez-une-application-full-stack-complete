import { Injectable } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { BehaviorSubject, filter } from 'rxjs';

/**
 * Service to manage and provide information about the current route.
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

    // Subscribing to router events to update the current route
    this.router.events
      .pipe(filter((event) => event instanceof NavigationEnd)) // Filtering only NavigationEnd events
      .subscribe(() => {
        const currentRoute = this.getCurrentRoute();
        this.currentRouteSubject.next(currentRoute); // Updating the current route subject
      });
  }

  /**
   * @returns {string} The current route.
   */
  getCurrentRoute(): string {
    return this.router.routerState.snapshot.url;
  }

  /**
   * Gets an observable of the current route.
   * @returns An observable emitting the current route.
   */
  getCurrentRoute$() {
    return this.currentRouteSubject.asObservable();
  }
}
