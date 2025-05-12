import { inject } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  RouterStateSnapshot,
} from '@angular/router';

import { Observable, filter, map } from 'rxjs';
import { SessionService } from '../core/session.service';

/**
 * Authentication guard to protect routes that require user authentication.
 * Redirects to the home page if the user is not logged in.
 * @param route - The activated route snapshot.
 * @param state - The router state snapshot.
 * @returns A boolean indicating whether the user is authenticated or an observable that resolves to a boolean.
 */
export const isAuthGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
): boolean | Observable<boolean> => {
  const sessionService = inject(SessionService);
  const router = inject(Router);

  /**
   * Function to check the login state of the user.
   * If the user is not logged in, redirects to the home page.
   * @returns A boolean indicating whether the user is logged in.
   */
  const checkLoginState = (): boolean => {
    if (sessionService.isLogged) {
      return true;
    } else {
      router.navigateByUrl('/home');
      return false;
    }
  };

  // If loading state is not in progress, check the login state immediately
  if (!sessionService.isLoading) {
    return checkLoginState();
  } else {
    // If loading state is in progress, wait for it to complete and then check the login state
    return sessionService.$isLoading().pipe(
      filter((isLoading: boolean) => !isLoading),
      map(() => {
        return checkLoginState();
      })
    );
  }
};
