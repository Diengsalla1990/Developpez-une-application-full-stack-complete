package com.openclassrooms.mddapi.serviceInterface;

import java.util.Optional;

import com.openclassrooms.mddapi.model.User;

public interface UserInterface {
	/**
	* Enregistre un utilisateur.
	*
	* @param User L'utilisateur à enregistrer.
	* @return L'utilisateur enregistré.
	*/
	  User saveUser(User dbUser);

	  /**
	  * Récupère l'adresse e-mail d'un utilisateur.
	  *
	  * @param email L'adresse e-mail de l'utilisateur à récupérer.
	  * @return Un champ facultatif contenant l'utilisateur s'il est trouvé, vide sinon.
	  */
	  Optional<User> getUserByEmail(String email);
	  /**
	  * Récupère un utilisateur par nom d'utilisateur.
	  *
	  * @param username Le nom d'utilisateur de l'utilisateur à récupérer.
	  * @return Un champ facultatif contenant l'utilisateur s'il est trouvé, vide sinon.
	  */
	  Optional<User> getUserByUsername(String username);

	  /**
	  * Récupère un utilisateur par identifiant.
	  *
	  * @param id : identifiant de l'utilisateur à récupérer.
	  * @return : optionnel contenant l'utilisateur s'il est trouvé, vide sinon.
	  */
	  Optional<User> getUserById(long id);

	  /**
	  * Récupère un utilisateur par identifiant et les sujets associés sont récupérés rapidement.
	  *
	  * @param id : identifiant de l'utilisateur à récupérer.
	  * @return : optionnel contenant l'utilisateur s'il est trouvé, vide sinon.
	  */
	  Optional<User> getUserByIdWithSub(long id);

	  /**
	  * Vérifie si une adresse e-mail est déjà utilisée par un autre utilisateur.
	  *
	  * @param email L'adresse e-mail à vérifier.
	  * @return Vrai si l'adresse e-mail est déjà utilisée, faux sinon.
	  */
	  boolean isEmailAlreadyTaken(String email);

	  /**
	  * Vérifie si un nom d'utilisateur est déjà utilisé par un autre utilisateur.
	  *
	  * @param username Le nom d'utilisateur à vérifier.
	  * @return Vrai si le nom d'utilisateur est déjà utilisé, faux sinon.
	  */
	  boolean isUsernameAlreadyTaken(String username);

	  /**
	  * Vérifie si un mot de passe correspond au mot de passe haché d'un utilisateur.
	  *
	  * @param password Mot de passe en clair à vérifier.
	  * @param dbUser Utilisateur dont le mot de passe doit être vérifié.
	  * @return Vrai si le mot de passe est valide, faux sinon.
	  */
	  boolean isPasswordValid(String password, User dbUser);

}
