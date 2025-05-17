package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Article;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import com.openclassrooms.mddapi.serviceInterface.ArticleInterface;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* Implémentation du service PostInterface.
*/
@Service
public class ArticleServiceImpl implements ArticleInterface {

  @Autowired
  private ArticleRepository articleRepository;

  /**
  * Enregistre une publication.
  *
  * @param post La publication à enregistrer.
  * @return La publication enregistrée.
  */
  @Override
  public Article saveArticle(Article article) {
    return articleRepository.save(article);
  }

  /**
  * Récupère une publication par identifiant.
  *
  * @param id : identifiant de la publication à récupérer.
  * @return : optionnel contenant la publication si elle est trouvée, vide sinon.
  */
  @Override
  public Optional<Article> getArticleById(long id) {
    return articleRepository.findById(id);
  }

  /**
  * Récupère une publication par identifiant et les commentaires associés, récupérés rapidement.
  *
  * @param id : identifiant de la publication à récupérer.
  * @return : optionnel contenant la publication si elle est trouvée, vide sinon.
  */
  @Override
  public Optional<Article> getArticleWithCommentsById(long id) {
    return articleRepository
      .findById(id)
      .map(post -> {
        post.getComments().size();
        return post;
      });
  }

}