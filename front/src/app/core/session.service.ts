import { Injectable } from '@angular/core';

import { BehaviorSubject, Observable, catchError, map, of, tap } from 'rxjs';
import { User } from './model/User.model';
import { UserService } from './api/user.service';
import { UserResponse } from './api/interface/user/response/UserResponse';

/**
* Service chargé de gérer les informations de session utilisateur.
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
* Observable pour suivre l'état de connexion.
* @returns Un Observable<booléen> indiquant si l'utilisateur est connecté.
*/
  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  /**
* Observable pour suivre l'état de chargement.
* @returns Un Observable<booléen> indiquant si le service est en cours de chargement.
*/
  public $isLoading(): Observable<boolean> {
    return this.isLoadingSubject.asObservable();
  }

  /**
* Observable pour récupérer des informations sur l'utilisateur connecté.
* @returns Un Observable<User | undefined> contenant des informations sur l'utilisateur.
*/
  public $getUser(): Observable<User | undefined> {
    return this.userSubject.asObservable();
  }

  /**
* Tente de se connecter à l'aide du jeton Web JSON (JWT) stocké dans le stockage local.
* Si un JWT est trouvé, il récupère les informations de l'utilisateur et met à jour la session en conséquence.
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
* Connecte l'utilisateur avec le jeton Web JSON (JWT) fourni.
* @param jwt Le JWT utilisé pour l'authentification de l'utilisateur.
* @returns Un Observable<booléen> indiquant si la connexion a réussi.
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
* Déconnecte l'utilisateur, efface les variables de session et supprime le JWT du stockage local.
*/
  public logOut() {
    this.isLogged = false;
    this.user = undefined;
    this.jwt = null;
    localStorage.removeItem('jwt');
    this.next();
  }

/**
* Définit l'état de chargement.
* @param value : une valeur booléenne indiquant si le service est en cours de chargement.
*/
  public setIsLoading(value: boolean) {
    this.isLoading = value;
  }

  /**
* Met à jour les informations utilisateur et transmet les modifications aux abonnés.
* @param user : les informations utilisateur mises à jour.
*/
  public updateUser(user: User) {
    this.user = user;
    this.userSubject.next(this.user);
  }

 /** Émet les changements d'état de session aux abonnés. */  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
    this.userSubject.next(this.user);
  }
}
