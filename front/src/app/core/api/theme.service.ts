import { Injectable } from '@angular/core';
import { Theme } from '../model/Theme.model';
import { BehaviorSubject, catchError, map, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { SubjectResponse } from './interface/subject/SubjectResponse';
import getErrorMessageFromCatchedError from './common/errorResponse';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
private pathService = 'api/theme/';

  themes!: Theme[];
  themeTheme = new BehaviorSubject<Theme[]>(this.themes);

  constructor(private http: HttpClient) {}

  /**
* Obtient un observable de sujets.
* @returns Un observable de sujets.
*/
  public $getThemes() {
    return this.themeTheme.asObservable();
  }

 /**
* Récupère tous les sujets.
* @returns Un observable des sujets récupérés.
*/
  public getAllSubjects(): Observable<Theme[]> {
   // Envoie une requête GET pour récupérer tous les sujets et gère la réponse
    return this.http.get<SubjectResponse[]>(this.pathService + 'getall').pipe(
      // Mappe la réponse à un tableau d'objets Subject
      map((response: SubjectResponse[]) => {
        let themeArr = response.map((subject: SubjectResponse) =>
          this.getThemeFromThemeResponse(subject)
        );
        //  Met à jour le tableau des sujets locaux et notifie les abonnés
        this.themes = themeArr;
        this.next();
        return themeArr;
      }),
      // Gère les erreurs en extrayant le message d'erreur et en lançant un observable d'erreur
      catchError((error: any) => getErrorMessageFromCatchedError(error))
    );
  }

  /**
* Récupère un sujet par son identifiant.
* @param subjectId - L'identifiant du sujet à récupérer.
* @returns Un observable du sujet récupéré.
*/
  public getThemeById(themeId: number): Observable<Theme> {
  // Envoie une requête GET pour récupérer un sujet par son ID et gère la réponse
    return this.http
      .get<SubjectResponse>(this.pathService + 'getbyid/' + themeId)
      .pipe(
       // Mappe la réponse à un objet Subject
        map((response: SubjectResponse) =>
          this.getThemeFromThemeResponse(response)
        ),
        // Gère les erreurs en extrayant le message d'erreur et en lançant un observable d'erreur
        catchError((error: any) => getErrorMessageFromCatchedError(error))
      );
  }

 /**
* Récupère un sujet contenant des publications par son identifiant.
* @param subjectId : identifiant du sujet à récupérer.
* @returns : un observable du sujet récupéré contenant des publications.
*/

  public getSubjectWithPostById(themeId: number): Observable<Theme> {
    return this.http
      .get<SubjectResponse>(this.pathService + 'getbyidwitharticle/' + themeId)
      .pipe(
        map((response: SubjectResponse) =>
          this.getThemeFromThemeResponse(response)
        ),
        catchError((error: any) => getErrorMessageFromCatchedError(error))
      );
  }

 /**
* Convertit un objet SubjectResponse en objet Subject.
* @param response : l'objet SubjectResponse à convertir.
* @returns : l'objet Subject converti.
*/
  private getThemeFromThemeResponse(response: SubjectResponse): Theme {
   // Si la réponse contient des postDtos, créez un objet Subject avec des publications
    if (response.articleDtos && response.articleDtos.length > 0) {
      const { id, name, description, articleDtos } = response;
      return new Theme(id, name, description, articleDtos);
    } else {
      // Si la réponse ne contient pas de postDtos, créez un objet Subject sans posts
      const { id, name, description } = response;
      return new Theme(id, name, description);
    }
  }

 /**
* Avertit les abonnés des changements de sujet.
*/
  private next() {
    this.themeTheme.next(this.themes);
  }
}
