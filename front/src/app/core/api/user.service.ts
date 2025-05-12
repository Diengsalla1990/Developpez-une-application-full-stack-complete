import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map } from 'rxjs';

import { UserResponse } from './interface/user/response/UserResponse';
import { UpdateUserRequest } from './interface/user/request/UpdateUserRequest';
import { User } from '../model/User.model';
import getErrorMessageFromCatchedError from './common/errorResponse';


/**
 * Service handling user-related operations.
 */
@Injectable({
  providedIn: 'root',
})
export class UserService {
  private pathService = 'api/user/';

  constructor(private http: HttpClient) {}

  /**
   * Retrieves the current user's information.
   * @returns An observable containing the current user's information.
   */
  public getMe(): Observable<User> {
    return this.http.get<UserResponse>(this.pathService + 'me').pipe(
      map((response: UserResponse) => this.getUserFromUserResponse(response)),
      catchError((error: any) => getErrorMessageFromCatchedError(error))
    );
  }

  /**
   * Retrieves the current user's information along with subscription details.
   * @returns An observable containing the current user's information with subscription details.
   */
  public getMeWithSub(): Observable<User> {
    return this.http.get<UserResponse>(this.pathService + 'mewithsub').pipe(
      map((response: UserResponse) => this.getUserFromUserResponse(response)),
      catchError((error: any) => getErrorMessageFromCatchedError(error))
    );
  }

  /**
   * Updates the current user's information.
   * @param updateUserRequest - The updated user information.
   * @returns An observable containing the updated user information.
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
   * Subscribes the current user to a subject.
   * @param subjectId - The ID of the subject to subscribe to.
   * @returns An observable containing the updated user information after subscription.
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
   * Unsubscribes the current user from a subject.
   * @param subjectId - The ID of the subject to unsubscribe from.
   * @returns An observable containing the updated user information after unsubscription.
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
   * Converts a UserResponse object to a User object.
   * @param response - The UserResponse object to convert.
   * @returns The converted User object.
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
