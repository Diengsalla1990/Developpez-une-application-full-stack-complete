import { Component, Input } from '@angular/core';
import { Article } from '../../core/model/Article.model';
import { Router } from '@angular/router';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-article-card',
  imports: [],
  templateUrl: './article-card.component.html',
  styleUrl: './article-card.component.css'
})
export class ArticleCardComponent {

  @Input() articleProps!: Article;

  constructor(private router: Router) {}

  /**
   * Navigates to the detailed view of the clicked post.
   */
  onArticleClick() {
    // Navigates to the detailed view of the post using its ID
    this.router.navigateByUrl(`/article/${this.articleProps.id}`);
  }
}
