import { Injectable } from '@angular/core';
import { PostResponse } from './interface/post/response/ArticleResponse';
import { Article } from '../model/Article.model';
import getPostFromPostResponse from './common/getPostFromPostResponse';
import { catchError, map, Observable } from 'rxjs';
import getErrorMessageFromCatchedError from './common/errorResponse';
import { CreatePostRequest } from './interface/post/request/CreatePostRequest';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

   private pathService = 'api/article/';

  constructor(private http: HttpClient) {}

 /**
* Crée une nouvelle publication.
* @param createPostRequest - Le corps de la requête de création de la publication.
* @returns Un observable de la publication créée.
*/
  public create(createPostRequest: CreatePostRequest): Observable<Article> {
    return this.http
      .post<PostResponse>(this.pathService + 'create', createPostRequest)
      .pipe(
        map((response: PostResponse) => getPostFromPostResponse(response)),
        catchError((error: any) => getErrorMessageFromCatchedError(error))
      );
  }

 /**
* Récupère une publication par son identifiant.
* @param postId - L'identifiant de la publication à récupérer.
* @returns Un observable de la publication récupérée.
*/
  public getById(articleId: number): Observable<Article> {
    return this.http
      .get<PostResponse>(this.pathService + 'getarticle/' + articleId)
      .pipe(
        map((response: PostResponse) => getPostFromPostResponse(response)),
        catchError((error: any) => getErrorMessageFromCatchedError(error))
      );
  }
}
