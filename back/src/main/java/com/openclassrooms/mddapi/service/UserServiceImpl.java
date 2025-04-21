package com.openclassrooms.mddapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.serviceInterface.UserInterface;

import jakarta.transaction.Transactional;


@Service
public class UserServiceImpl implements UserInterface {
	  @Autowired
	  private UserRepository dbUserRepository;

	  @Autowired
	  private BCryptPasswordEncoder passwordEncoder;

	  /**
	  * Enregistre un utilisateur.
	  *
	  * @param dbUser L'utilisateur à enregistrer.
	  * @return L'utilisateur enregistré.
	  */
	  @Override
	  public User saveUser(User dbUser) {
	    return dbUserRepository.save(dbUser);
	  }

	  /**
	  * Récupère l'adresse e-mail d'un utilisateur.
	  *
	  * @param email L'adresse e-mail de l'utilisateur à récupérer.
	  * @return Un champ facultatif contenant l'utilisateur s'il est trouvé, vide sinon.
	  */
	  @Override
	  public Optional<User> getUserByEmail(String email) {
	    return dbUserRepository.findByEmail(email);
	  }
	  /**
	  * Récupère un utilisateur par nom d'utilisateur.
	  *
	  * @param username Le nom d'utilisateur de l'utilisateur à récupérer.
	  * @return Un champ facultatif contenant l'utilisateur s'il est trouvé, vide sinon.
	  */
	  @Override
	  public Optional<User> getUserByUsername(String username) {
	    return dbUserRepository.findByUsername(username);
	  }

	  /**
	  * Récupère un utilisateur par identifiant.
	  *
	  * @param id : identifiant de l'utilisateur à récupérer.
	  * @return : optionnel contenant l'utilisateur s'il est trouvé, vide sinon.
	  */
	  @Override
	  public Optional<User> getUserById(long id) {
	    return dbUserRepository.findById(id);
	  }

	  /**
	  * Récupère un utilisateur par identifiant et les Themes associés sont récupérés rapidement.
	  *
	  * @param id : identifiant de l'utilisateur à récupérer.
	  * @return : optionnel contenant l'utilisateur s'il est trouvé, vide sinon.
	  */
	  @Override
	  @Transactional
	  public Optional<User> getUserByIdWithSub(long id) {
	    return dbUserRepository
	      .findById(id)
	      .map(dbUser -> {
	        dbUser.getThemes().size();
	        return dbUser;
	      });
	  }

	  /**
	  * Vérifie si une adresse e-mail est déjà utilisée par un autre utilisateur.
	  *
	  * @param email L'adresse e-mail à vérifier.
	  * @return Vrai si l'adresse e-mail est déjà utilisée, faux sinon.
	  */

	  @Override
	  public boolean isEmailAlreadyTaken(String email) {
	    Optional<User> optionalUser = dbUserRepository.findByEmail(email);
	    return optionalUser.isPresent();
	  }

	  /**
	  * Vérifie si un nom d'utilisateur est déjà utilisé par un autre utilisateur.
	  *
	  * @param username Le nom d'utilisateur à vérifier.
	  * @return Vrai si le nom d'utilisateur est déjà utilisé, faux sinon.
	  */
	  @Override
	  public boolean isUsernameAlreadyTaken(String username) {
	    Optional<User> optionalUser = dbUserRepository.findByUsername(username);
	    return optionalUser.isPresent();
	  }

	  /**
	  * Vérifie si un mot de passe correspond au mot de passe haché d'un utilisateur.
	  *
	  * @param password Mot de passe en clair à vérifier.
	  * @param dbUser Utilisateur dont le mot de passe doit être vérifié.
	  * @return Vrai si le mot de passe est valide, faux sinon.
	  */
	  @Override
	  public boolean isPasswordValid(String password, User dbUser) {
	    return passwordEncoder.matches(password, dbUser.getPassword());
	  }

	

}
