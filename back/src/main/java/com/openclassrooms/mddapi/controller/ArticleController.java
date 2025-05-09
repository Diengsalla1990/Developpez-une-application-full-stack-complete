package com.openclassrooms.mddapi.controller;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.model.Commentaire;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.JwtServiceImpl;
import com.openclassrooms.mddapi.serviceInterface.ArticleInterface;
import com.openclassrooms.mddapi.serviceInterface.ThemeInterface;
import com.openclassrooms.mddapi.serviceInterface.UserInterface;
import com.openclassrooms.mddapi.util.entityAndDtoCreation.EntityAndDtoCreation;
import com.openclassrooms.mddapi.util.payload.Request.ArticleRequest;
import com.openclassrooms.mddapi.util.payload.Response.ArticleReponse;
import com.openclassrooms.mddapi.util.payload.Response.UserResponse;

/**
 * Controller for handling post-related operations.
 */
@RestController
@RequestMapping("/api/article")
public class ArticleController {

  @Autowired
  private ArticleInterface articleService;

  @Autowired
  private EntityAndDtoCreation entityAndDtoCreation;

  @Autowired
  private JwtServiceImpl jwtService;

  @Autowired
  private UserInterface UserService;

  @Autowired
  private ThemeInterface themeService;

  @Autowired
  private ArticleReponse articleResponse;

  @Autowired
  private UserResponse UserResponse;

  /**
  * Crée une nouvelle publication.
  *
  * @param jwt Le jeton JWT obtenu à partir de la requête.
  * @param postRequest La charge utile de la requête contenant les données de la publication.
  * @param bindingResult Le résultat de la validation de la charge utile de la requête.
  * @return ResponseEntity contenant le DTO et le statut HTTP de la publication créée.
  */
  @PostMapping("/create")
  public ResponseEntity<?> createPost(
    @AuthenticationPrincipal Jwt jwt,
    @Valid @RequestBody ArticleRequest articleRequest,
    BindingResult bindingResult
  ) {
    try {
      boolean isRequestPayloadInvalid = bindingResult.hasErrors();
      if (isRequestPayloadInvalid) {
        return ResponseEntity
          .badRequest()
          .body(articleResponse.ArticleInvalidePayload());
      }

      long userId = jwtService.getUserIdFromJwtLong(jwt);
      Optional<User> optionalDbUser = UserService.getUserById(userId);
      if (!optionalDbUser.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(UserResponse.getMeNoUserFoundWithThisJwtTokenId());
      }

      User dbUser = optionalDbUser.get();

      Optional<Theme> optionalArticle = themeService.getThemeById(
        articleRequest.getThemeId()
      );
      if (!optionalArticle.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(articleResponse.postPostInvalidSubjectId());
      }

      Theme theme = optionalArticle.get();

      Article article = new Article();
      article
        .setTitle(articleRequest.getTitle())
        .setContent(articleRequest.getContent())
        .setUser(dbUser)
        .setTheme(theme)
        .setCreatedAt(LocalDateTime.now())
        .setComments(new ArrayList<Commentaire>());

      Article savedPost = articleService.saveArticle(article);

      return ResponseEntity
        .ok()
        .body(
          entityAndDtoCreation.getArtWithCommentsDtoFromArtWithCommentEntity(
            savedPost
          )
        );
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  /**
  * Récupère une publication par son identifiant.
  *
  * @param postId : identifiant de la publication à récupérer.
  * @return ResponseEntity : entité contenant le DTO et le statut HTTP de la publication récupérée.
  */
  @GetMapping("/getarticle/{articleId}")
  public ResponseEntity<?> getArticleById(@PathVariable final long articleId) {
    try {
      Optional<Article> optionalArticle = articleService.getArticleWithCommentsById(articleId);
      if (!optionalArticle.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(articleResponse.getPostInvalidPostId());
      }

      Article article = optionalArticle.get();

      return ResponseEntity
        .ok()
        .body(
          entityAndDtoCreation.getArtWithCommentsDtoFromArtWithCommentEntity(
        		  article
          )
        );
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }
}