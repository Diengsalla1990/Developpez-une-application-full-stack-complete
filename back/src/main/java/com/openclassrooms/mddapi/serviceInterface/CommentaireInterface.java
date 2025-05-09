package com.openclassrooms.mddapi.serviceInterface;

import com.openclassrooms.mddapi.model.Commentaire;

/**
 * Interface for managing Comment entities.
 */
public interface CommentaireInterface {
  /**
   * Saves a comment.
   *
   * @param comment The comment to save.
   * @return The saved comment.
   */
  Commentaire saveComment(Commentaire comment);
}