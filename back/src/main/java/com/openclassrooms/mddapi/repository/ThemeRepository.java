package com.openclassrooms.mddapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.mddapi.model.Theme;

/**
* Interface de référentiel pour la gestion des entités Theme.
* Étend JpaRepository pour hériter des opérations CRUD de base.
*/
public interface ThemeRepository extends JpaRepository<Theme,Long> {

}
