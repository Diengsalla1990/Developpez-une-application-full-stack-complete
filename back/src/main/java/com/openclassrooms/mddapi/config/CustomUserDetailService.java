package com.openclassrooms.mddapi.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;


/**
 * Implémentation personnalisée de UserDetailsService pour charger 
 * les détails de l'utilisateur à partir de la base de données.
 */
@Service
public class CustomUserDetailService implements UserDetailsService{


	  @Autowired
	  private UserRepository dbUserRepository;

	  /**
	  * Charger les informations de l'utilisateur par e-mail.
	  *
	  * @param email : adresse e-mail de l'utilisateur.
	  * @return : objet UserDetails contenant les informations de l'utilisateur.
	  * @throws : UsernameNotFoundException si l'utilisateur avec l'e-mail donné est introuvable.

	   */
	  @Override
	  public UserDetails loadUserByUsername(String email)
	    throws UsernameNotFoundException {
	    Optional<User> optionalUser = dbUserRepository.findByEmail(email);

	    User dbUser = optionalUser.orElseThrow(() ->
	      new UsernameNotFoundException("User not found with email: " + email)
	    );

	    // Returning UserDetails object with user's email and password
	    return (UserDetails) new User(dbUser.getEmail(), dbUser.getPassword(), null);
	  }

}
