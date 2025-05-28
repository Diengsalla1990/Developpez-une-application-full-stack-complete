import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CreateCommentRequest } from './interface/comment/request/CreateCommentRequest';
import { Article } from '../model/Article.model';
import { catchError, map, Observable } from 'rxjs';
import { PostResponse } from './interface/post/response/ArticleResponse';
import getPostFromPostResponse from './common/getPostFromPostResponse';
import getErrorMessageFromCatchedError from './common/errorResponse';

@Injectable({
  providedIn: 'root'
})
export class CommentService {
private pathService = 'api/comment/';

  constructor(private http: HttpClient) {}
/**
* Crée un nouveau commentaire.
* @param commentRequest - Le corps de la requête pour la création du commentaire.
* @returns Un observable de la publication mise à jour après la création du commentaire.
*/
  public createComment(commentRequest: CreateCommentRequest): Observable<Article> {
    return this.http
      .post<PostResponse>(this.pathService + 'create', commentRequest)
      .pipe(
        map((response: PostResponse) => getPostFromPostResponse(response)),
        catchError((error: any) => getErrorMessageFromCatchedError(error))
      );
  }
}
