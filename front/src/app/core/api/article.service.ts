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
   * Creates a new post.
   * @param createPostRequest - The request body for creating the post.
   * @returns An Observable of the created Post.
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
   * Retrieves a post by its ID.
   * @param postId - The ID of the post to retrieve.
   * @returns An Observable of the retrieved Post.
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
