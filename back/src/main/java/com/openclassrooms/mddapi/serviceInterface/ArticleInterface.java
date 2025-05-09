package com.openclassrooms.mddapi.serviceInterface;


import java.util.Optional;

import com.openclassrooms.mddapi.model.Article;

/**
 * Interface for managing Post entities.
 */
public interface ArticleInterface {
	
  Article saveArticle(Article article);

  Optional<Article> getArticleById(long id);

  Optional<Article> getArticleWithCommentsById(long id);
}