import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map } from 'rxjs';
import { RegisterRequest } from './interface/userAuth/request/RegisterRequest';
import { LoginRequest } from './interface/userAuth/request/LoginRequest';
import { LoginResponse } from './interface/userAuth/response/LoginResponse';
import { RegisterResponse } from './interface/userAuth/response/RegisterResponse';

/**
 * Service handling user authentication operations.
 */
@Injectable({
  providedIn: 'root',
})
export class UserAuthService {
  private pathService = 'api/auth/';

  constructor(private http: HttpClient) {}

/**
* Enregistre un nouvel utilisateur.
* @param {RegisterRequest} registerRequest - Données de la demande d'enregistrement.
* @returns {Observable<booléen>} Un observable indiquant la réussite de l'enregistrement.
*/  
public register(registerRequest: RegisterRequest): Observable<boolean> {
    return this.http
      .post<HttpResponse<RegisterResponse>>(
        this.pathService + 'register',
        registerRequest,
        { observe: 'response' } // permet d'observer la réponse « complète » du backend, pas seulement le corps de la réponse, car nous recherchons l'état de la réponse http

      )
      .pipe(
        map((response: any) => {
          if (response.ok) {
            return response.ok;
          }
        }),
        catchError((error: any) => {
          throw error;
        })
      );
  }

/**
* Connecte un utilisateur existant.
* @param {LoginRequest} loginRequest - Données de la demande de connexion.
* @returns {Observable<string>} Observable contenant le jeton utilisateur lors d'une connexion réussie.
*/
  public login(loginRequest: LoginRequest): Observable<string> {
    return this.http
      .post<LoginResponse>(this.pathService + 'login', loginRequest)
      .pipe(
        map((response: LoginResponse) => {
          if (response.token) {
            return response.token;
          } else {
            throw new Error(response.message);
          }
        })
      );
  }
}
