package com.openclassrooms.mddapi.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.serviceInterface.ThemeInterface;
import com.openclassrooms.mddapi.util.entityAndDtoCreation.EntityAndDtoCreation;
import com.openclassrooms.mddapi.util.payload.Response.ThemeReponse;

/**

* Contrôleur pour la gestion des opérations liées au sujet.
*/

@RestController
@RequestMapping("/api/theme")
public class ThemeController {

  @Autowired
  private ThemeInterface themeService;

  @Autowired
  private EntityAndDtoCreation entityAndDtoCreation;

  @Autowired
  private ThemeReponse themeResponse;

  /**
  * Récupère tous les themes.
  *
  * @return ResponseEntity contenant la liste des themes en cas de succès, ou une réponse d'erreur en cas d'exception.
  */
  @GetMapping("/getall")
  public ResponseEntity<?> getAllTheme() {
    try {
      List<Theme> themes = themeService.getAllTheme();
      return ResponseEntity
        .ok()
        .body(
          entityAndDtoCreation.getThemeDtoListFromThemeEntityList(themes)
        );
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  /**
  * Récupère un sujet par son identifiant.
  *
  * @param themeId : identifiant du theme à récupérer.
  * @return ResponseEntity : contenant le theme s'il est trouvé, ou une réponse d'erreur si l'identifiant est invalide ou si une exception se produit.
  */
  @GetMapping("/getbyid/{themeId}")
  public ResponseEntity<?> getThemeById(@PathVariable final long themeId) {
    try {
      Optional<Theme> optionalTheme = themeService.getThemeById(
    		  themeId
      );
      if (!optionalTheme.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(themeResponse.getSubjectInvalidIdParameter());
      }

      Theme theme = optionalTheme.get();

      return ResponseEntity
        .ok()
        .body(entityAndDtoCreation.getSubjectDtoFromSubjectEntity(theme));
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  /**
  * Récupère un theme par son identifiant et les publications associées.
  *
  * @param subjectId : identifiant du theme à récupérer.
  * @return ResponseEntity : contenant le theme et les publications associées s'il est trouvé, ou une réponse d'erreur si l'identifiant est invalide ou si une exception se produit.
  */
  @GetMapping("/getbyidwitharticle/{themeId}")
  public ResponseEntity<?> getSubjectByIdWithPost(
    @PathVariable final long themeId
  ) {
    try {
      Optional<Theme> optionalSubject = themeService.getThemeByIdWithPost(
    		  themeId
      );
      if (!optionalSubject.isPresent()) {
        return ResponseEntity
          .badRequest()
          .body(themeResponse.getSubjectInvalidIdParameter());
      }

      Theme theme = optionalSubject.get();

      return ResponseEntity
        .ok()
        .body(
          entityAndDtoCreation.getSubjectWithPostDtoFromSubjectWithPostEntity(
            theme
          )
        );
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }
}