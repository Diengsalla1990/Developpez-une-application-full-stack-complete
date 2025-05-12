import { Injectable } from '@angular/core';

import { BehaviorSubject, Observable, catchError, map, of, tap } from 'rxjs';
import { User } from './model/User.model';
import { UserService } from './api/user.service';
import { UserResponse } from './api/interface/user/response/UserResponse';

/**
 * Service responsible for managing user session information.
 */
@Injectable({
  providedIn: 'root',
})
export class SessionService {
  public isLogged = false;
  public user: User | undefined = undefined;
  public jwt: String | null = null;
  public isLoading: boolean = true;

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);
  private isLoadingSubject = new BehaviorSubject<boolean>(this.isLoading);
  private userSubject = new BehaviorSubject<User | undefined>(this.user);

  constructor(private userService: UserService) {}

  /**
   * Observable to track the login status.
   * @returns An Observable<boolean> indicating whether the user is logged in.
   */
  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  /**
   * Observable to track the loading status.
   * @returns An Observable<boolean> indicating whether the service is in a loading state.
   */
  public $isLoading(): Observable<boolean> {
    return this.isLoadingSubject.asObservable();
  }

  /**
   * Observable to retrieve information about the logged-in user.
   * @returns An Observable<User | undefined> containing information about the user.
   */
  public $getUser(): Observable<User | undefined> {
    return this.userSubject.asObservable();
  }

  /**
   * Attempts to log in using the JSON Web Token (JWT) stored in the local storage.
   * If a JWT is found, it fetches user details and updates the session accordingly.
   */
  public loginWithLocalStorageJwt(): void {
    const jwt = localStorage.getItem('jwt');
    if (jwt !== null) {
      this.userService
        .getMeWithSub()
        .pipe(
          tap((user: UserResponse) => {
            this.user = user;
            this.isLogged = true;
            this.jwt = jwt;
            this.isLoading = false;
            this.next();
            this.isLoadingSubject.next(this.isLoading);
          }),
          catchError((error) => {
            this.isLogged = false;
            this.user = undefined;
            this.jwt = null;
            localStorage.removeItem('jwt');
            this.isLoading = false;
            this.next();
            this.isLoadingSubject.next(this.isLoading);
            return [];
          })
        )
        .subscribe();
    } else {
      this.isLoading = false;
      this.isLoadingSubject.next(this.isLoading);
    }
  }

  /**
   * Logs in the user with the provided JSON Web Token (JWT).
   * @param jwt The JWT used for user authentication.
   * @returns An Observable<boolean> indicating whether the login was successful.
   */
  public logIn(jwt: string): Observable<boolean> {
    const localStorageJwt = localStorage.getItem('jwt');
    if (localStorageJwt === null || localStorageJwt !== jwt) {
      localStorage.setItem('jwt', jwt);
    }
    return this.userService.getMeWithSub().pipe(
      map((user: UserResponse) => {
        this.user = user;
        this.isLogged = true;
        this.jwt = jwt;
        this.next();
        return true;
      }),
      catchError((error) => {
        this.isLogged = false;
        this.user = undefined;
        this.jwt = null;
        localStorage.removeItem('jwt');
        this.next();
        return of(false);
      })
    );
  }

  /**
   * Logs out the user, clearing session variables and removing the JWT from local storage.
   */
  public logOut() {
    this.isLogged = false;
    this.user = undefined;
    this.jwt = null;
    localStorage.removeItem('jwt');
    this.next();
  }

  /**
   * Sets the loading status.
   * @param value A boolean indicating whether the service is in a loading state.
   */
  public setIsLoading(value: boolean) {
    this.isLoading = value;
  }

  /**
   * Updates the user information and emits changes to subscribers.
   * @param user The updated user information.
   */
  public updateUser(user: User) {
    this.user = user;
    this.userSubject.next(this.user);
  }

  /** Emits changes of session states to subscribers. */
  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
    this.userSubject.next(this.user);
  }
}
