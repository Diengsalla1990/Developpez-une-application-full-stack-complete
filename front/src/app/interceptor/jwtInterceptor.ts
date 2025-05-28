import { HttpHandlerFn, HttpHeaders, HttpRequest } from '@angular/common/http';
import { inject } from '@angular/core';

import { jwtDecode } from 'jwt-decode';
import { Router } from '@angular/router';
import { SessionService } from '../core/session.service';

/**
* Fonction d'interception pour ajouter un jeton JWT aux en-têtes des requêtes HTTP,
* sauf pour les requêtes adressées à des points de terminaison liés à l'authentification.
* @param req - Requête HTTP interceptée.
* @param next - Gestionnaire HTTP de la requête interceptée.
* @returns Requête HTTP avec jeton JWT ajouté aux en-têtes.
*/
export function jwtInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn) {
  const sessionService = inject(SessionService);
  const router = inject(Router)

  if (!req.url.includes('/auth')) {
    const jwt = localStorage.getItem('jwt');

    // Vérifiez si le JWT et la date d'expiration du JWT sont valides pour la demande maintenant
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
