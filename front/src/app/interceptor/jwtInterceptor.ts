import { HttpHandlerFn, HttpHeaders, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';

import { jwtDecode } from 'jwt-decode';
import { Router } from '@angular/router';
import { SessionService } from '../core/session.service';

/**
 * Interceptor function to add JWT token to the headers of HTTP requests,
 * except for requests to authentication-related endpoints.
 * @param req - The HTTP request being intercepted.
 * @param next - The HTTP handler for the intercepted request.
 * @returns The HTTP request with JWT token added to the headers.
 */
export function jwtInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn) {
  const sessionService = inject(SessionService);
  const router = inject(Router)

  if (!req.url.includes('/auth')) {
    const jwt = localStorage.getItem('jwt');

    // Check if JWT and JWT expiration date is valid for request now
    if(jwt) {
      const decodedToken = jwtDecode(jwt);
      if(decodedToken.exp && Date.now() > decodedToken.exp * 1000) {
        sessionService.logOut();
        router.navigateByUrl("/home")
      }
    } else {
      sessionService.logOut();
      router.navigateByUrl("/home")
    }

    const headers = new HttpHeaders().append('Authorization', `Bearer ${jwt}`);
    req = req.clone({ headers });
  }

  return next(req);
}
