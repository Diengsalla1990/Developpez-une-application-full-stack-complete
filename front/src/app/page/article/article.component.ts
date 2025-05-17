import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { NavComponent } from '../../component/nav/nav.component';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { catchError, Observable, of, throwError } from 'rxjs';
import { Article } from '../../core/model/Article.model';
import { Commentaire } from '../../core/model/Commentaire.model';
import { ActivatedRoute, Router } from '@angular/router';
import { ArticleService } from '../../core/api/article.service';
import { CommentService } from '../../core/api/comment.service';
import { PostResponse } from '../../core/api/interface/post/response/ArticleResponse';
import { CreateCommentRequest } from '../../core/api/interface/comment/request/CreateCommentRequest';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-article',
  imports: [CommonModule,NavComponent,ReactiveFormsModule],
  templateUrl: './article.component.html',
  styleUrl: './article.component.css'
})
export class ArticleComponent implements OnInit {
  
  comment$!: Observable<Commentaire>;
  article$!: Observable<Article>;
  errorOnIdParameter!: boolean;
  isLoading = true;
  commentForm!: FormGroup;
  commentHasError = false;
  articleId!: string | null;
  articleIdToInt!: number;
  commentServiceError = false;
  commentData!: Commentaire


   private destroyRef: DestroyRef = inject(DestroyRef);

  constructor(
    private router: Router,
    private articleService: ArticleService,
    private commentService: CommentService,
    private activatedRoute: ActivatedRoute,
    private formBuilder: FormBuilder
  ) {}
  ngOnInit(): void {// get id url parameter and fetch post with id, assign to post$ if success or trigger error handling logic
    this.articleId = this.activatedRoute.snapshot.paramMap.get('id');

    if (!this.articleId) {
      this.errorOnIdParameter = true;
    } else {
      this.articleIdToInt = parseInt(this.articleId);

      if (Number.isNaN(this.articleIdToInt)) {
        this.errorOnIdParameter = true;
      } else {
        this.article$ = this.articleService.getById(this.articleIdToInt).pipe(
          takeUntilDestroyed(this.destroyRef),
          catchError((error) => {
            console.error(error);
            this.errorOnIdParameter = true;
            this.isLoading = false;
            return throwError(() => error);
          })
        );
        this.isLoading = false;
      }
    }

    this.commentForm = this.formBuilder.group({
      comment: [
        '',
        [
          Validators.required,
          Validators.pattern(/^(?=.*[a-zA-Z0-9!@#$%^&*()-_+=])[\s\S]*$/),
        ],
      ],
    });
  }

    backToArticles() {
    this.router.navigateByUrl('/articles');
  }

  /**
   * Submits a comment.
   * Validates comment content before submission.
   * If content is valid, creates a comment using the comment service.
   */
  submitComment() {
    this.commentHasError = false;
    this.commentServiceError = false;

    const commentControl = this.commentForm.controls['comment'];

    const commentContent = commentControl.value;
    const isCommentContentValid = commentControl.valid;

    // If the comment content is not valid, set error flag and return
    if (!isCommentContentValid) {
      this.commentHasError = true;
      return;
    }

    const commentRequest: CreateCommentRequest = {
      articleId: this.articleIdToInt,
      content: commentContent,
    };

    this.commentService.createComment(commentRequest).subscribe({
      next: (response: PostResponse) => {
        // Update the post with the response (updated post with comments)
        this.article$ = of(response);
        this.commentForm.get('comment')?.setValue('');
      },
      error: (error: any) => {
        console.log(error);
        // Set error flag for the comment service
        this.commentServiceError = true;
      },
    });
  }

}


