package com.openclassrooms.mddapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.mddapi.model.User;

/**
* Interface de référentiel pour la gestion des entités DbUser.
* Étend JpaRepository pour hériter des opérations CRUD de base.
*/

public interface UserRepository extends JpaRepository<User,Long>{
		
		/**
		* Rechercher un utilisateur par son adresse e-mail.
		*
		* @param email L'adresse e-mail de l'utilisateur.
		* @return Un champ facultatif contenant l'utilisateur s'il est trouvé, vide sinon.
		*/
	  Optional<User> findByEmail(String email);

	  /**
	  * Rechercher un utilisateur par son nom d'utilisateur.
	  *
	  * @param username Le nom d'utilisateur de l'utilisateur.
	  * @return Un champ facultatif contenant l'utilisateur s'il est trouvé, vide sinon.
	  */
	  Optional<User> findByUsername(String username);

}
