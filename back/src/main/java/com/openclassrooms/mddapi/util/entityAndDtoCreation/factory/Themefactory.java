package com.openclassrooms.mddapi.util.entityAndDtoCreation.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.ThemeDto;
import com.openclassrooms.mddapi.model.Theme;

/**
 * Factory class for creating Subject DTOs.
 */
@Component
public class Themefactory {

  @Autowired
  private Articlefactory articleFactory;

  /**
   * Converts a Subject entity to a SubjectDto.
   *
   * @param subject The Subject entity to convert.
   * @return The corresponding SubjectDto.
   */
  public ThemeDto getThemeDtoFromEntity(Theme theme) {
    return new ThemeDto()
      .setId(theme.getId())
      .setName(theme.getName())
      .setDescription(theme.getDescription());
  }

  /**
   * Converts a Subject entity with associated posts to a SubjectDto with post DTOs.
   *
   * @param subject The Subject entity with associated posts.
   * @return The corresponding SubjectDto with post DTOs.
   */
  public ThemeDto getThemeWithPostListToDto(Theme theme) {
    return new ThemeDto(
    		theme.getId(),
    		theme.getName(),
    		theme.getDescription(),
      articleFactory.getArticleDtoListFromArticletEntityList(theme.getArticles())
    );
  }
}