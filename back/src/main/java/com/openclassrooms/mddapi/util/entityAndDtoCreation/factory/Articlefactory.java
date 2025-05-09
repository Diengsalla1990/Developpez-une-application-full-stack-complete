package com.openclassrooms.mddapi.util.entityAndDtoCreation.factory;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.model.Article;

/**
 * Factory class for creating Post DTOs.
 */
@Component
public class Articlefactory {

  @Autowired
  private Commentairefactory commentFactory;

  /**
   * Converts a Post entity to a PostDto.
   *
   * @param post The Post entity to convert.
   * @return The corresponding PostDto.
   */
  public ArticleDto getArticleDtoFromArticleEntity(Article article) {
    return new ArticleDto()
      .setId(article.getId())
      .setTitle(article.getTitle())
      .setContent(article.getContent())
      .setAuteurId(article.getUser().getId())
      .setAuteurName(article.getUser().getUsername())
      .setThemeId(article.getTheme().getId())
      .setThemeName(article.getTheme().getName())
      .setCreatedAt(article.getCreatedAt());
  }

  /**
   * Converts a list of Post entities to a list of PostDto objects.
   *
   * @param postList The list of Post entities to convert.
   * @return The list of corresponding PostDto objects.
   */
  public List<ArticleDto> getArticleDtoListFromArticletEntityList(List<Article> articleList) {
    return articleList
      .stream()
      .map(post -> getArticleDtoFromArticleEntity(post))
      .collect(Collectors.toList());
  }

  /**
   * Converts a Post entity with associated comments to a PostDto with comment DTOs.
   *
   * @param post The Post entity with associated comments.
   * @return The corresponding PostDto with comment DTOs.
   */
  public ArticleDto getPostWithCommentToDto(Article article) {
    return new ArticleDto()
      .setId(article.getId())
      .setTitle(article.getTitle())
      .setContent(article.getContent())
      .setAuteurId(article.getUser().getId())
      .setAuteurName(article.getUser().getUsername())
      .setThemeId(article.getTheme().getId())
      .setThemeName(article.getTheme().getName())
      .setCreatedAt(article.getCreatedAt())
      .setCommentDtos(commentFactory.commentListToDto(article.getComments()));
  }
}