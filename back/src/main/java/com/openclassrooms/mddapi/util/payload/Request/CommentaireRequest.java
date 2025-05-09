package com.openclassrooms.mddapi.util.payload.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data model for creation of comment request.
 */
@Data
public class CommentaireRequest {

  /**
   * The ID of the post to which the comment belongs.
   */
  @NotNull
  private long articleId;

  /**
   * The content of the comment.
   */
  @NotNull
  private String content;
}