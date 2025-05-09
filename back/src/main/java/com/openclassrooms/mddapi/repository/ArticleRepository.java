package com.openclassrooms.mddapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.mddapi.model.Article;
/**
* Interface de référentiel pour la gestion des entités Article.
* Étend JpaRepository pour hériter des opérations CRUD de base.
*/

public interface ArticleRepository extends JpaRepository<Article,Long>{

}
