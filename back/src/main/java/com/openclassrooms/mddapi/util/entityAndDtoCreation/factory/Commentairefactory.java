package com.openclassrooms.mddapi.util.entityAndDtoCreation.factory;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.dto.CommentaireDto;
import com.openclassrooms.mddapi.model.Commentaire;

/**
 * Factory class for creating Comment DTOs.
 */
@Component
public class Commentairefactory {

  /**
   * Converts a Comment entity to a CommentDto.
   *
   * @param comment The Comment entity to convert.
   * @return The corresponding CommentDto.
   */
  public CommentaireDto commentToDto(Commentaire comment) {
    return new CommentaireDto(
      comment.getId(),
      comment.getContent(),
      comment.getUser().getId(),
      comment.getUser().getUsername(),
      comment.getCreatedAt(),
      comment.getArticle().getId()
    );
  }

  /**
   * Converts a list of Comment entities to a list of CommentDto objects.
   *
   * @param commentList The list of Comment entities to convert.
   * @return The list of corresponding CommentDto objects.
   */
  public List<CommentaireDto> commentListToDto(List<Commentaire> commentList) {
    return commentList
      .stream()
      .map(comment -> commentToDto(comment))
      .collect(Collectors.toList());
  }
}