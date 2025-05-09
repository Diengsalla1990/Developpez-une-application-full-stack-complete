package com.openclassrooms.mddapi.util.entityAndDtoCreation;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.ArticleDto;
import com.openclassrooms.mddapi.dto.ThemeDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.util.entityAndDtoCreation.factory.Articlefactory;
import com.openclassrooms.mddapi.util.entityAndDtoCreation.factory.Themefactory;
import com.openclassrooms.mddapi.util.entityAndDtoCreation.factory.Userfactory;
import com.openclassrooms.mddapi.util.payload.Request.RegisterRequest;

/**
 * Utility class for creating entities and DTOs.
 */
@Component
public class EntityAndDtoCreation {

  @Autowired
  private Userfactory dbUserFactory;
  
  @Autowired
  private Themefactory themeFactory;
  
  @Autowired
  private Articlefactory articlefactory;
  
  
  /**
   * Converts a DbUser entity to a DbUserDto.
   *
   * @param dbUser The DbUser entity to convert.
   * @return The corresponding DbUserDto.
   */
  public UserDto getDbUserDtoFromDbUserEntity(User dbUser) {
    return dbUserFactory.getUserDTOFromEntity(dbUser);
  }

  /**
   * Converts a DbUser entity with associated subjects to a DbUserDto with subject IDs.
   *
   * @param dbUser The DbUser entity with associated subjects.
   * @return The corresponding DbUserDto with subject IDs.
   */
  public UserDto getDbUserWithSubIdsFromDbUserWithSubEntity(User dbUser) {
    return dbUserFactory.getUserWithSubDtoFromEntityWithSub(dbUser);
  }


  

  /**
   * Converts a RegisterRequest object to a DbUser entity.
   *
   * @param registerRequest The RegisterRequest object to convert.
   * @return The corresponding DbUser entity.
   */
  public User getDbUserFromRegisterRequest(RegisterRequest registerRequest) {
    return dbUserFactory.getUserEntityFromRegisterRequest(registerRequest);
  }
  
  
  /**
   * Converts a Subject entity to a SubjectDto.
   *
   * @param subject The Subject entity to convert.
   * @return The corresponding SubjectDto.
   */
  public ThemeDto getSubjectDtoFromSubjectEntity(Theme subject) {
    return themeFactory.getThemeDtoFromEntity(subject);
  }

  /**
   * Converts a list of Subject entities to a list of SubjectDto objects.
   *
   * @param subjectList The list of Subject entities to convert.
   * @return The list of corresponding SubjectDto objects.
   */
  public List<ThemeDto> getThemeDtoListFromThemeEntityList(
    List<Theme> themeList
  ) {
    return themeList
      .stream()
      .map(this::getSubjectDtoFromSubjectEntity)
      .collect(Collectors.toList());
  }

  /**
   * Converts a Subject entity with associated posts to a SubjectDto with post DTOs.
   *
   * @param subject The Subject entity with associated posts.
   * @return The corresponding SubjectDto with post DTOs.
   */
  public ThemeDto getSubjectWithPostDtoFromSubjectWithPostEntity(
    Theme subject
  ) {
    return themeFactory.getThemeWithPostListToDto(subject);
  }
  
  
  /**
   * Converts a Post entity to a PostDto.
   *
   * @param post The Post entity to convert.
   * @return The corresponding PostDto.
   */
  public ArticleDto getArtDtoFromArtEntity(Article article) {
    return articlefactory.getArticleDtoFromArticleEntity(article);
  }

  /**
   * Converts a Post entity with associated comments to a PostDto with comment DTOs.
   *
   * @param post The Post entity with associated comments.
   * @return The corresponding PostDto with comment DTOs.
   */
  public ArticleDto getArtWithCommentsDtoFromArtWithCommentEntity(Article article) {
    return articlefactory.getPostWithCommentToDto(article);
  }
  
  

  
}