package com.openclassrooms.mddapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.mddapi.model.Commentaire;


public interface CommentaireRepository extends JpaRepository<Commentaire,Long>{
   
}
