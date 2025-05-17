package com.openclassrooms.mddapi.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.JwtServiceImpl;
import com.openclassrooms.mddapi.serviceInterface.ArticleInterface;
import com.openclassrooms.mddapi.serviceInterface.ThemeInterface;
import com.openclassrooms.mddapi.serviceInterface.UserInterface;
import com.openclassrooms.mddapi.util.entityAndDtoCreation.EntityAndDtoCreation;
import com.openclassrooms.mddapi.util.payload.Request.UpdateProfileRequest;
import com.openclassrooms.mddapi.util.payload.Response.UserAuthResponse;
import com.openclassrooms.mddapi.util.payload.Response.UserResponse;

/**
* Contrôleur pour la gestion des opérations liées à l'utilisateur.
*/
@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  private UserInterface dbUserService;

  @Autowired
  private UserResponse userResponse;

  @Autowired
  private UserAuthResponse dbUserAuthResponse;

  @Autowired
  private EntityAndDtoCreation entityAndDtoCreation;

  @Autowired
  private JwtServiceImpl jwtService;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private ThemeInterface themeService;

  /**
  * Récupère l'utilisateur actuellement authentifié.
  *
  * @param jwt Le jeton JWT représentant l'utilisateur actuel.
  * @return ResponseEntity contenant les informations de l'utilisateur si elles sont trouvées, ou une réponse d'erreur si elles ne sont pas trouvées ou si une erreur survient.
  */
  @GetMapping("/me")
  public ResponseEntity<?> getMe(@AuthenticationPrincipal Jwt jwt) {
    try {
    	// Extraire l'ID utilisateur du jeton JWT
      long userId = jwtService.getUserIdFromJwtLong(jwt);

   // Récupérer l'utilisateur de la base de données
      Optional<User> optionalDbUser = dbUserService.getUserById(userId);

   // Vérifier si l'utilisateur existe
      if (!optionalDbUser.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(userResponse.getMeNoUserFoundWithThisJwtTokenId());
      }

   // Convertir l'entité DbUser en DTO
      UserDto dbUserDto = entityAndDtoCreation.getDbUserDtoFromDbUserEntity(
        optionalDbUser.get()
      );

   // Renvoyer le DTO utilisateur dans la réponse
      return ResponseEntity.ok().body(dbUserDto);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  /**
  * Récupère l'utilisateur actuellement authentifié ainsi que ses sujets abonnés.
  *
  * @param jwt Le jeton JWT représentant l'utilisateur actuel.
  * @return ResponseEntity contenant les informations de l'utilisateur et les sujets abonnés s'ils sont trouvés, ou une réponse d'erreur s'ils ne sont pas trouvés ou si une erreur survient.
  */
  @GetMapping("/mewithsub")
  public ResponseEntity<?> getMeWithSub(@AuthenticationPrincipal Jwt jwt) {
    try {
    	// Extraire l'ID utilisateur du jeton JWT
      long userId = jwtService.getUserIdFromJwtLong(jwt);

   // Récupérer l'utilisateur avec les sujets abonnés de la base de données
      Optional<User> optionalDbUser = dbUserService.getUserByIdWithSub(
        userId
      );

      // verifier si user existe
      if (!optionalDbUser.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(userResponse.getMeNoUserFoundWithThisJwtTokenId());
      }

   // Convertir l'entité DbUser avec les sujets abonnés en DTO
      return ResponseEntity
        .ok()
        .body(
          entityAndDtoCreation.getDbUserWithSubIdsFromDbUserWithSubEntity(
            optionalDbUser.get()
          )
        );
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  /**
  * Met à jour le profil de l'utilisateur actuellement authentifié.
  *
  * @param jwt Le jeton JWT représentant l'utilisateur actuel.
  * @param updateProfileRequest Le corps de la requête contenant les informations de profil mises à jour.
  * @param bindingResult Le résultat de la validation du corps de la requête.
  * @return ResponseEntity contenant les informations de l'utilisateur mises à jour avec les sujets abonnés si la mise à jour est réussie, ou une réponse d'erreur si elle est introuvable, si la validation échoue ou si une erreur survient.
  */
  @PutMapping("/updateme")
  public ResponseEntity<?> updateMe(
    @AuthenticationPrincipal Jwt jwt,
    @Valid @RequestBody UpdateProfileRequest updateProfileRequest,
    BindingResult bindingResult
  ) {
    try {
      boolean isRequestPayloadInvalid = bindingResult.hasErrors();
      if (isRequestPayloadInvalid) {
        return ResponseEntity
          .badRequest()
          .body(userResponse.putUpdateProfilInvalidCredentials());
      }

      long userId = jwtService.getUserIdFromJwtLong(jwt);

      Optional<User> optionalDbUser = dbUserService.getUserById(userId);

      if (!optionalDbUser.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(userResponse.getMeNoUserFoundWithThisJwtTokenId());
      }

   // Obtenir l'entité utilisateur
      User dbUser = optionalDbUser.get();

   // Créer une nouvelle instance DbUser pour la mise à jour
      User updatedDbUser = new User();
      updatedDbUser.setId(dbUser.getId());
      updatedDbUser.setComments(dbUser.getComments());
      updatedDbUser.setArticles(dbUser.getArticles());
      updatedDbUser.setThemes(dbUser.getThemes());

   // Mettre à jour l'e-mail s'il est différent de l'actuel
      if (!updateProfileRequest.getEmail().equals(dbUser.getEmail())) {
        boolean isEmailAlreadyTaken = dbUserService.isEmailAlreadyTaken(
          updateProfileRequest.getEmail()
        );
        if (isEmailAlreadyTaken) {
          return ResponseEntity
            .badRequest()
            .body(
              dbUserAuthResponse.getRegisteringEmailAlreadyUsedResponseMessage()
            );
        }

        updatedDbUser.setEmail(updateProfileRequest.getEmail());
      } else {
        updatedDbUser.setEmail(dbUser.getEmail());
      }

   // Mettre à jour le nom d'utilisateur s'il est différent du nom actuel
      if (!updateProfileRequest.getUsername().equals(dbUser.getUsername())) {
        boolean isUsernameAlreadyTaken = dbUserService.isUsernameAlreadyTaken(
          updateProfileRequest.getUsername()
        );
        if (isUsernameAlreadyTaken) {
          return ResponseEntity
            .badRequest()
            .body(
              dbUserAuthResponse.getRegisterUserameAlreadyUsedResponseMessage()
            );
        }

        updatedDbUser.setUsername(updateProfileRequest.getUsername());
      } else {
        updatedDbUser.setUsername(dbUser.getUsername());
      }

   // Mettre à jour le mot de passe si fourni
      if (updateProfileRequest.getPassword() != null) {
        updatedDbUser.setPassword(
          bCryptPasswordEncoder.encode(updateProfileRequest.getPassword())
        );
      } else {
        updatedDbUser.setPassword(dbUser.getPassword());
      }

      User savedUpdatedDbUser = dbUserService.saveUser(updatedDbUser);

      return ResponseEntity
        .ok()
        .body(
          entityAndDtoCreation.getDbUserWithSubIdsFromDbUserWithSubEntity(
            savedUpdatedDbUser
          )
        );
    } catch (Exception e) {
      System.err.println(e);
      return ResponseEntity.internalServerError().build();
    }
  }

  /**
  * Abonne l'utilisateur actuel à un sujet spécifié.
  *
  * @param jwt Le jeton JWT représentant l'utilisateur actuel.
  * @param subjectId L'ID du sujet auquel s'abonner.
  * @return ResponseEntity contenant les informations de l'utilisateur mises à jour avec les sujets abonnés si l'abonnement est réussi, ou une réponse d'erreur si l'ID du sujet est invalide, si l'utilisateur est introuvable, s'il est déjà abonné au sujet ou si une erreur survient.
  */
  @PostMapping("/subscribe/{themeId}")
  public ResponseEntity<?> subscribe(
    @AuthenticationPrincipal Jwt jwt,
    @PathVariable final long themeId
  ) {
    try {
    	// Obtenir tous les sujets de la base de données
      List<Theme> themesList = themeService.getAllTheme();
   // Vérifier si l'ID du sujet est présent dans la liste des sujets
      boolean isSubjectIdPresentInSubjectList = isThemeIdPresent(
        themesList,
        themeId
      );
      if (!isSubjectIdPresentInSubjectList) {
        return ResponseEntity
          .badRequest()
          .body(userResponse.subscriptionInvalidParameterSubjectId());
      }

      long userId = jwtService.getUserIdFromJwtLong(jwt);

      Optional<User> optionalDbUser = dbUserService.getUserByIdWithSub(
        userId
      );

      if (!optionalDbUser.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(userResponse.getMeNoUserFoundWithThisJwtTokenId());
      }

      User dbUser = optionalDbUser.get();

   // Vérifier si l'utilisateur est déjà abonné au sujet
      boolean isUserAlreadySubOfThisSubject = isThemeIdPresent(
        dbUser.getThemes(),
        themeId
      );
      if (isUserAlreadySubOfThisSubject) {
        return ResponseEntity
          .badRequest()
          .body(userResponse.subscriptionUserAlreadySubscribed());
      }

   // Ajouter le sujet à la liste des sujets abonnés de l'utilisateur
      List<Theme> themes = dbUser.getThemes();
      themes.add(themeService.getThemeById(themeId).get());

      User savedDbUser = dbUserService.saveUser(dbUser);

   // Renvoyer le DTO utilisateur mis à jour dans la réponse
      return ResponseEntity
        .ok()
        .body(
          entityAndDtoCreation.getDbUserWithSubIdsFromDbUserWithSubEntity(
            savedDbUser
          )
        );
    } catch (MethodArgumentTypeMismatchException | NumberFormatException e) {
      return ResponseEntity
        .badRequest()
        .body(userResponse.subscriptionInvalidParameterSubjectIdType());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  /**
  * Désabonne l'utilisateur actuel d'un sujet spécifié.
  *
  * @param jwt Jeton JWT représentant l'utilisateur actuel.
  * @param subjectId ID du sujet à désabonner.
  * @return ResponseEntity contenant les informations mises à jour de l'utilisateur avec les sujets abonnés si la désinscription est réussie, ou une réponse d'erreur si l'ID du sujet est invalide, si l'utilisateur est introuvable, s'il n'est pas abonné au sujet ou si une erreur survient.
  */
  @DeleteMapping("/unsubscribe/{themeId}")
  public ResponseEntity<?> unsubscribe(
    @AuthenticationPrincipal Jwt jwt,
    @PathVariable final long themeId
  ) {
    try {
    	// Obtenir tous les sujets de la base de données
      List<Theme> themesList = themeService.getAllTheme();
   // Vérifier si l'ID du sujet est présent dans la liste des sujets
      boolean isSubjectIdPresentInSubjectList = isThemeIdPresent(
    		  themesList,
    		  themeId
      );
      if (!isSubjectIdPresentInSubjectList) {
        return ResponseEntity
          .badRequest()
          .body(userResponse.subscriptionInvalidParameterSubjectId());
      }

      long userId = jwtService.getUserIdFromJwtLong(jwt);

      Optional<User> optionalDbUser = dbUserService.getUserByIdWithSub(
        userId
      );

      if (!optionalDbUser.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(userResponse.getMeNoUserFoundWithThisJwtTokenId());
      }

      User dbUser = optionalDbUser.get();

   // Vérifier si l'utilisateur est déjà abonné au sujet
      boolean isUserAlreadySubOfThisSubject = isThemeIdPresent(
        dbUser.getThemes(),
        themeId
      );
      if (!isUserAlreadySubOfThisSubject) {
        return ResponseEntity
          .badRequest()
          .body(userResponse.subscriptionUserNotSubscribedToThisSubject());
      }

   // Supprimer le sujet de la liste des sujets abonnés de l'utilisateur
      List<Theme> subjects = dbUser.getThemes();
      subjects.removeIf(subject -> subject.getId() == themeId);

      User savedDbUser = dbUserService.saveUser(dbUser);

   // Renvoyer le DTO utilisateur mis à jour dans la réponse
      return ResponseEntity
        .ok()
        .body(
          entityAndDtoCreation.getDbUserWithSubIdsFromDbUserWithSubEntity(
            savedDbUser
          )
        );
    } catch (MethodArgumentTypeMismatchException | NumberFormatException e) {
      return ResponseEntity
        .badRequest()
        .body(userResponse.subscriptionInvalidParameterSubjectIdType());
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  private boolean isThemeIdPresent(List<Theme> themes, long targetId) {
    return themes.stream().anyMatch(subject -> subject.getId() == targetId);
  }
}