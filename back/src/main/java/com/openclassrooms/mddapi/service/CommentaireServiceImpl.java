package com.openclassrooms.mddapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.Commentaire;
import com.openclassrooms.mddapi.repository.CommentaireRepository;
import com.openclassrooms.mddapi.serviceInterface.CommentaireInterface;


/**
 * Implementation of the CommentInterface service.
 */
@Service
public class CommentaireServiceImpl implements CommentaireInterface {
	
	  @Autowired
	  private CommentaireRepository commentRepository;

	  /**
	   * Saves a comment.
	   *
	   * @param comment The comment to save.
	   * @return The saved comment.
	   */
	  @Override
	  public Commentaire saveComment(Commentaire comment) {
	    return commentRepository.save(comment);
	  }

}
