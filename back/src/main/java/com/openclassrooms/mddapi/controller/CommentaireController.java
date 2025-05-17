package com.openclassrooms.mddapi.controller;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Commentaire;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.JwtServiceImpl;
import com.openclassrooms.mddapi.serviceInterface.ArticleInterface;
import com.openclassrooms.mddapi.serviceInterface.CommentaireInterface;
import com.openclassrooms.mddapi.serviceInterface.UserInterface;
import com.openclassrooms.mddapi.util.entityAndDtoCreation.EntityAndDtoCreation;
import com.openclassrooms.mddapi.util.payload.Request.CommentaireRequest;
import com.openclassrooms.mddapi.util.payload.Response.ArticleReponse;
import com.openclassrooms.mddapi.util.payload.Response.UserResponse;

/**
* Contrôleur pour la gestion des opérations liées aux commentaires.
*/
@RestController
@RequestMapping("/api/comment")
public class CommentaireController {

  @Autowired
  private CommentaireInterface commentService;

  @Autowired
  private JwtServiceImpl jwtService;

  @Autowired
  private EntityAndDtoCreation entityAndDtoCreation;

  @Autowired
  private UserInterface userService;

  @Autowired
  private UserResponse dbUserResponse;

  @Autowired
  private ArticleInterface articleService;

  @Autowired
  private ArticleReponse articleResponse;

  /**
  * Point de terminaison pour la création d'un commentaire.
  * @param jwt Le jeton JWT obtenu à partir de la requête.
  * @param commentRequest La charge utile de la requête contenant les données du commentaire.
  * @return ResponseEntity contenant la réponse à la requête.
  */
  @PostMapping("/create")
  public ResponseEntity<?> createComment(
    @AuthenticationPrincipal Jwt jwt,
    @Valid @RequestBody CommentaireRequest commentRequest
  ) {
    try {
    	// Récupérer la publication en fonction de l'ID de publication fourni
      Optional<Article> optionalArticle = articleService.getArticleById(
        commentRequest.getArticleId()
      );
      if (!optionalArticle.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(articleResponse.getPostInvalidPostId());
      }
      Article article = optionalArticle.get();

   // Récupérer l'utilisateur en fonction du jeton JWT
      long userId = jwtService.getUserIdFromJwtLong(jwt);
      Optional<User> optionalDbUser = userService.getUserById(userId);
      if (!optionalDbUser.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(dbUserResponse.getMeNoUserFoundWithThisJwtTokenId());
      }
      User dbUser = optionalDbUser.get();

      // Creation d'un commentaire
      Commentaire comment = new Commentaire();
      comment.setContent(commentRequest.getContent());
      comment.setUser(dbUser);
      comment.setArticle(article);
      comment.setCreatedAt(LocalDateTime.now());

      // Save commentaire
      Commentaire savedComment = commentService.saveComment(comment);

   // Récupérer le message mis à jour avec les commentaires
      List<Commentaire> commentList = article.getComments();

   // Renvoyer la réponse avec le message mis à jour
      return ResponseEntity
        .ok()
        .body(
          entityAndDtoCreation.getArtWithCommentsDtoFromArtWithCommentEntity(
            article
          )
        );
    } catch (Exception e) {
    	// Renvoyer une réponse d'erreur interne du serveur si une exception se produit
      return ResponseEntity.internalServerError().build();
    }
  }
}