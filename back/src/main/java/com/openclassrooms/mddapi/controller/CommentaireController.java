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
 * Controller for handling operations related to comments.
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
   * Endpoint for creating a new comment.
   * @param jwt The JWT token obtained from the request.
   * @param commentRequest The request payload containing the comment data.
   * @return ResponseEntity containing the response to the request.
   */
  @PostMapping("/create")
  public ResponseEntity<?> createComment(
    @AuthenticationPrincipal Jwt jwt,
    @Valid @RequestBody CommentaireRequest commentRequest
  ) {
    try {
      // Retrieve the post based on the provided post ID
      Optional<Article> optionalArticle = articleService.getArticleById(
        commentRequest.getArticleId()
      );
      if (!optionalArticle.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(articleResponse.getPostInvalidPostId());
      }
      Article article = optionalArticle.get();

      // Retrieve the user based on the JWT token
      long userId = jwtService.getUserIdFromJwtLong(jwt);
      Optional<User> optionalDbUser = userService.getUserById(userId);
      if (!optionalDbUser.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(dbUserResponse.getMeNoUserFoundWithThisJwtTokenId());
      }
      User dbUser = optionalDbUser.get();

      // Create the comment
      Commentaire comment = new Commentaire();
      comment.setContent(commentRequest.getContent());
      comment.setUser(dbUser);
      comment.setArticle(article);
      comment.setCreatedAt(LocalDateTime.now());

      // Save the comment
      Commentaire savedComment = commentService.saveComment(comment);

      // Retrieve the updated post with comments
      List<Commentaire> commentList = article.getComments();

      // Return the response with the updated post
      return ResponseEntity
        .ok()
        .body(
          entityAndDtoCreation.getArtWithCommentsDtoFromArtWithCommentEntity(
            article
          )
        );
    } catch (Exception e) {
      // Return an internal server error response if an exception occurs
      return ResponseEntity.internalServerError().build();
    }
  }
}