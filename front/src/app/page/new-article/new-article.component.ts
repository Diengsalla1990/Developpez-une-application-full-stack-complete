import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { catchError, Observable, Subscription } from 'rxjs';
import { ArticleService } from '../../core/api/article.service';
import { ThemeService } from '../../core/api/theme.service';
import { SessionService } from '../../core/session.service';
import { Theme } from '../../core/model/Theme.model';
import { CreatePostRequest } from '../../core/api/interface/post/request/CreatePostRequest';
import { PostResponse } from '../../core/api/interface/post/response/ArticleResponse';
import { NavComponent } from '../../component/nav/nav.component';

@Component({
  selector: 'app-new-article',
  imports: [NavComponent,ReactiveFormsModule, CommonModule],
  templateUrl: './new-article.component.html',
  styleUrl: './new-article.component.css'
})
export class NewArticleComponent {

  themeList$!: Observable<Theme[]>;
  sujectFetchError = false;
  articleForm!: FormGroup;
  themeHasError = false;
  themeIsNotPlaceholderSelection = false;
  titleHasError = false;
  contentHasError = false;
  submitHasError = false;
  submitSuccess = false;
  isSubmitting = false;

  articleServiceSubscription!: Subscription;

  constructor(
    private router: Router,
    private articleService: ArticleService,
    private formBuilder: FormBuilder,
    private themeService: ThemeService,
    private sessionService: SessionService
  ) {}


  backToArticles() {
    this.router.navigateByUrl('/articles');
  }

  /** Handles the change event when selecting a theme */
  onSelectChange() {
    if (this.articleForm.controls['theme'].value !== 'Sélectionner un thème') {
      this.themeIsNotPlaceholderSelection = true;
    }
  }

  ngOnInit(): void {
    this.themeList$ = this.themeService.$getThemes().pipe(
      catchError((error: any) => {
        this.sujectFetchError = true;
        return [];
      })
    );

    this.articleForm = this.formBuilder.group({
      theme: [null, [Validators.required]],
      title: [null, [Validators.required, Validators.maxLength(60)]],
      content: [null, [Validators.required, Validators.minLength(10)]],
    });
  }


   onSubmitForm() {
    if (!this.isSubmitting) {
      this.isSubmitting = true;
      this.themeHasError = false;
      this.titleHasError = false;
      this.contentHasError = false;
      this.submitHasError = false;

      const themeControl = this.articleForm.controls['theme'];
      const themeContent = themeControl.value;
      const isThemeValid = themeControl.valid;

      if (!isThemeValid) {
        this.themeHasError = true;
      }

      const titleControl = this.articleForm.controls['title'];
      const titleContent = titleControl.value;
      const isTitleValid = titleControl.valid;

      if (!isTitleValid) {
        this.titleHasError = true;
      }

      const contentControl = this.articleForm.controls['content'];
      const contentContent = contentControl.value;
      const isContentValid = contentControl.valid;

      if (!isContentValid) {
        this.contentHasError = true;
      }

      
      if (this.themeHasError || this.titleHasError || this.contentHasError) {
        this.isSubmitting = false;
        return;
      }

      const themeId = parseInt(themeContent);

      const newPost: CreatePostRequest = {
        title: titleContent,
        content: contentContent,
        themeId: themeId,
      };

      // Envoyer une demande pour créer un nouveau message
      this.articleServiceSubscription = this.articleService
        .create(newPost)
        .subscribe({
          next: (response: PostResponse) => {
      
            this.submitSuccess = true;
            setTimeout(() => {
              this.router.navigateByUrl('/articles');
            }, 3000);
          },
          error: (error: any) => {
            console.log(error);
            this.submitHasError = true;
            this.isSubmitting = false;
          },
        });
    }
  }

  ngOnDestroy(): void {
    if (this.articleServiceSubscription) {
      this.articleServiceSubscription.unsubscribe();
    }
  }

}
