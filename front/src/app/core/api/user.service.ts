import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map } from 'rxjs';

import { UserResponse } from './interface/user/response/UserResponse';
import { UpdateUserRequest } from './interface/user/request/UpdateUserRequest';
import { User } from '../model/User.model';
import getErrorMessageFromCatchedError from './common/errorResponse';


/**
* Service gérant les opérations liées à l'utilisateur.
*/
@Injectable({
  providedIn: 'root',
})
export class UserService {
  private pathService = 'api/user/';

  constructor(private http: HttpClient) {}

/**
* Récupère les informations de l'utilisateur actuel.
* @returns Un observable contenant les informations de l'utilisateur actuel.
*/
  public getMe(): Observable<User> {
    return this.http.get<UserResponse>(this.pathService + 'me').pipe(
      map((response: UserResponse) => this.getUserFromUserResponse(response)),
      catchError((error: any) => getErrorMessageFromCatchedError(error))
    );
  }
/**
* Récupère les informations de l'utilisateur actuel ainsi que les détails de son abonnement.
* @returns Un observable contenant les informations de l'utilisateur actuel ainsi que les détails de son abonnement.
*/
  public getMeWithSub(): Observable<User> {
    return this.http.get<UserResponse>(this.pathService + 'mewithsub').pipe(
      map((response: UserResponse) => this.getUserFromUserResponse(response)),
      catchError((error: any) => getErrorMessageFromCatchedError(error))
    );
  }

  /**
* Met à jour les informations de l'utilisateur actuel.
* @param updateUserRequest - Informations utilisateur mises à jour.
* @returns Un observable contenant les informations utilisateur mises à jour.
*/
  public updateMe(updateUserRequest: UpdateUserRequest): Observable<User> {
    return this.http
      .put<UserResponse>(this.pathService + 'updateme', updateUserRequest)
      .pipe(
        map((response: UserResponse) => this.getUserFromUserResponse(response)),
        catchError((error: any) => getErrorMessageFromCatchedError(error))
      );
  }

  /**
* Abonne l'utilisateur actuel à un sujet.
* @param subjectId - L'ID du sujet auquel s'abonner.
* @returns Un observable contenant les informations utilisateur mises à jour après l'abonnement.
*/
  public subscribe(themeId: number): Observable<User> {
    return this.http
      .post<UserResponse>(this.pathService + 'subscribe/' + themeId, null)
      .pipe(
        map((response: UserResponse) => this.getUserFromUserResponse(response)),
        catchError((error: any) => getErrorMessageFromCatchedError(error))
      );
  }

  /**
* Désabonne l'utilisateur actuel d'un sujet.
* @param subjectId - L'ID du sujet à désabonner.
* @returns Un observable contenant les informations utilisateur mises à jour après la désinscription.
*/
  public unsubscribe(themeId: number): Observable<User> {
    return this.http
      .delete<UserResponse>(this.pathService + 'unsubscribe/' + themeId)
      .pipe(
        map((response: UserResponse) => this.getUserFromUserResponse(response)),
        catchError((error: any) => getErrorMessageFromCatchedError(error))
      );
  }

 /**
* Convertit un objet UserResponse en objet User.
* @param response - L'objet UserResponse à convertir.
* @returns L'objet User converti.
*/
  private getUserFromUserResponse(response: UserResponse): User {
    if (!response.themeIds) {
      const { id, email, username } = response;
      return new User(id, email, username);
    } else {
      const { id, email, username, themeIds } = response;
      return new User(id, email, username, themeIds);
    }
  }
}
