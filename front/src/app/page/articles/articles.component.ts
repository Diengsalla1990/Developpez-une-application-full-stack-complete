import { Component, OnDestroy, OnInit } from '@angular/core';
import { ButtonComponent } from '../../component/button/button.component';
import { ArticleCardComponent } from '../../component/article-card/article-card.component';
import { CommonModule } from '@angular/common';
import { filter, from, map, mergeMap, Observable, of, Subscription, switchMap } from 'rxjs';
import { Theme } from '../../core/model/Theme.model';
import { Article } from '../../core/model/Article.model';
import { Button } from '../../interface/Button.interface';
import { SessionService } from '../../core/session.service';
import { Router } from '@angular/router';
import { ThemeService } from '../../core/api/theme.service';
import { HeaderComponent } from "../../component/header/header.component";

@Component({
  selector: 'app-articles',
  imports: [ButtonComponent, ArticleCardComponent, CommonModule, HeaderComponent],
  templateUrl: './articles.component.html',
  styleUrl: './articles.component.css'
})
export class ArticlesComponent implements OnInit, OnDestroy{


  subscription$!: Observable<number[]>;
  theme: Theme[] = [];
  orderedPostsByDate: Article[] = [];
  isLoading = true;
  error = false;
  themeNumber = 0;
  themeCount = 0;

  buttonProps: Button = {
    text: 'Créer un article',
    colored: true,
  };

  subscription!: Subscription;

   constructor(
    private sessionService: SessionService,
    private themeService: ThemeService,
    private router: Router
  ) {}
  ngOnInit(): void {

     this.isLoading = true;

    // Fetches user subscription data
    this.subscription$ = this.sessionService.$getUser().pipe(
      filter((user) => user !== undefined), // Filters out undefined user objects
      map((user) => {
        if (user?.themeIds) {
          return user.themeIds; // Extracts subject IDs from user data
        } else {
          return [];
        }
      })
    );

    // Retrieves subjects with posts based on user subscription
    const themes$ = this.subscription$.pipe(
      switchMap((subscription) => {
        // If user is subscribed to any subjects
        if (subscription.length > 0) {
          this.themeNumber = subscription.length;
          // Convert subscription array to an Observable and use mergeMap
          // to fetch subjects concurrently based on each subscription ID
          return from(subscription).pipe(
            mergeMap((subId) =>
              this.themeService.getSubjectWithPostById(subId)
            )
          );
        } else {
          // If user is not subscribed to any subjects
          // Fetch all subjects and their posts
          return this.themeService.$getThemes().pipe(
            switchMap((themes) => {
              if (themes && themes.length > 0) {
                this.themeNumber = themes.length;
                // Convert subjects array to an Observable and use mergeMap
                // to fetch posts concurrently for each subject
                return from(themes).pipe(
                  mergeMap((theme: Theme) =>
                    this.themeService.getSubjectWithPostById(theme.id)
                  )
                );
              } else {
                return of();
              }
            })
          );
        }
      })
    );

    // Subscribes to the final stream of subjects with posts
    this.subscription = themes$.subscribe({
      next: (theme: Theme) => {
        this.themeCount++;

        // Extract and accumulate posts from the subject
        if (theme.article) {
          theme.article.forEach((article: Article) => {
            this.orderedPostsByDate.push(article);
          });
        }
        if (this.themeCount === this.themeNumber) {
          // if all requested subjects had been fetch, end loading state (complete)
          this.orderedPostsByDate = this.orderPostArrByDateDesc(); // Orders posts by date
          this.isLoading = false;
        }
      },
      error: (error: any) => {
        console.log(error);
        this.error = true; // Sets error flag on error
        this.isLoading = false;
      },
    });
  }

  // Orders posts by date in descending order
  private orderPostArrByDateDesc(): Article[] {
    return this.orderedPostsByDate.sort((a: Article, b: Article) => {
      const dateA = new Date(a.createdAt).getTime();
      const dateB = new Date(b.createdAt).getTime();
      return dateB - dateA;
    });
  }
 
  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  
  onPostCreateButtonClick() {
    this.router.navigateByUrl('/new-article');
  }

  

}
