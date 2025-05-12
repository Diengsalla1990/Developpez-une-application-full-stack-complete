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
   * Registers a new user.
   * @param {RegisterRequest} registerRequest - The registration request data.
   * @returns {Observable<boolean>} An observable indicating the success of the registration.
   */
  public register(registerRequest: RegisterRequest): Observable<boolean> {
    return this.http
      .post<HttpResponse<RegisterResponse>>(
        this.pathService + 'register',
        registerRequest,
        { observe: 'response' } // allow to observe 'full' backend response, not only response body cause we're looking for http response status
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
   * Logs in an existing user.
   * @param {LoginRequest} loginRequest - The login request data.
   * @returns {Observable<string>} An observable containing the user token upon successful login.
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
