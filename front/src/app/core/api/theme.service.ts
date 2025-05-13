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
   * Gets an observable of subjects.
   * @returns An Observable of subjects.
   */
  public $getThemes() {
    return this.themeTheme.asObservable();
  }

  /**
   * Retrieves all subjects.
   * @returns An Observable of the retrieved subjects.
   */
  public getAllSubjects(): Observable<Theme[]> {
    // Sends a GET request to retrieve all subjects and handles the response
    return this.http.get<SubjectResponse[]>(this.pathService + 'getall').pipe(
      // Maps the response to an array of Subject objects
      map((response: SubjectResponse[]) => {
        let themeArr = response.map((subject: SubjectResponse) =>
          this.getThemeFromThemeResponse(subject)
        );
        // Updates the local subjects array and notifies subscribers
        this.themes = themeArr;
        this.next();
        return themeArr;
      }),
      // Handles errors by extracting error message and throwing an Error observable
      catchError((error: any) => getErrorMessageFromCatchedError(error))
    );
  }

  /**
   * Retrieves a subject by its ID.
   * @param subjectId - The ID of the subject to retrieve.
   * @returns An Observable of the retrieved subject.
   */
  public getThemeById(themeId: number): Observable<Theme> {
    // Sends a GET request to retrieve a subject by its ID and handles the response
    return this.http
      .get<SubjectResponse>(this.pathService + 'getbyid/' + themeId)
      .pipe(
        // Maps the response to a Subject object
        map((response: SubjectResponse) =>
          this.getThemeFromThemeResponse(response)
        ),
        // Handles errors by extracting error message and throwing an Error observable
        catchError((error: any) => getErrorMessageFromCatchedError(error))
      );
  }

  /**
   * Retrieves a subject with posts by its ID.
   * @param subjectId - The ID of the subject to retrieve.
   * @returns An Observable of the retrieved subject with posts.
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
   * Converts a SubjectResponse object to a Subject object.
   * @param response - The SubjectResponse object to convert.
   * @returns The converted Subject object.
   */
  private getThemeFromThemeResponse(response: SubjectResponse): Theme {
    // If the response contains postDtos, create a Subject object with posts
    if (response.articleDtos && response.articleDtos.length > 0) {
      const { id, name, description, articleDtos } = response;
      return new Theme(id, name, description, articleDtos);
    } else {
      // If the response does not contain postDtos, create a Subject object without posts
      const { id, name, description } = response;
      return new Theme(id, name, description);
    }
  }

  /**
   * Notifies subscribers about changes in subjects.
   */
  private next() {
    this.themeTheme.next(this.themes);
  }
}
