package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.serviceInterface.ArticleInterface;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the PostInterface service.
 */
@Service
public class ArticleServiceImpl implements ArticleInterface {

  @Autowired
  private ArticleRepository articleRepository;

  /**
   * Saves a post.
   *
   * @param post The post to save.
   * @return The saved post.
   */
  @Override
  public Article saveArticle(Article article) {
    return articleRepository.save(article);
  }

  /**
   * Retrieves a post by ID.
   *
   * @param id The ID of the post to retrieve.
   * @return An Optional containing the post if found, empty otherwise.
   */
  @Override
  public Optional<Article> getArticleById(long id) {
    return articleRepository.findById(id);
  }

  /**
   * Retrieves a post by ID with associated comments eagerly fetched.
   *
   * @param id The ID of the post to retrieve.
   * @return An Optional containing the post if found, empty otherwise.
   */
  @Override
  public Optional<Article> getArticleWithCommentsById(long id) {
    return articleRepository
      .findById(id)
      .map(post -> {
        post.getComments().size();
        return post;
      });
  }

}