package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.Theme;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.serviceInterface.ThemeInterface;

/**
* Implémentation du service ThemeInterface.
*/
@Service
public class ThemeServiceImpl implements ThemeInterface {

  @Autowired
  private ThemeRepository themeRepository;

  /**
  * Récupère tous les sujets.
  *
  * @return La liste de tous les sujets.
  */

  @Override
  public List<Theme> getAllTheme() {
    return themeRepository.findAll();
  }

  /**
  * Récupère un sujet par identifiant.
  *
  * @param id : identifiant du sujet à récupérer.
  * @return : optionnel contenant le sujet s'il est trouvé, vide sinon.
  */
  @Override
  public Optional<Theme> getThemeById(long id) {
    return themeRepository.findById(id);
  }

  /**
  * Récupère un sujet par identifiant, avec les publications associées récupérées rapidement.
  *
  * @param id : identifiant du sujet à récupérer.
  * @return : optionnel contenant le sujet s'il est trouvé, vide sinon.
  */

  @Override
  public Optional<Theme> getThemeByIdWithPost(long id) {
    return themeRepository
      .findById(id)
      .map(theme -> {
        theme.getArticles().size();
        return theme;
      });
  }
}