package com.openclassrooms.mddapi.util.payload.Request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data model for post creation request.
 */
@Data
public class ArticleRequest {

  /**
   * le titre de Article.
   */
  @NotNull
  private String title;

  /**
  * Le contenu du message.
  * Il doit comporter au moins 10 caractères.
  */
  @NotNull
  @Size(min = 10)
  private String content;

  /**
  * L'identifiant du sujet auquel appartient le message.
  */
  @NotNull
  private long themeId;
}