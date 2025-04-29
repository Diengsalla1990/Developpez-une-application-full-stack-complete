package com.openclassrooms.mddapi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.serviceInterface.JwtInterface;
import com.openclassrooms.mddapi.serviceInterface.UserInterface;
import com.openclassrooms.mddapi.util.entityAndDtoCreation.EntityAndDtoCreation;
import com.openclassrooms.mddapi.util.payload.Request.LoginRequest;
import com.openclassrooms.mddapi.util.payload.Request.RegisterRequest;
import com.openclassrooms.mddapi.util.payload.Response.UserAuthResponse;

import jakarta.validation.Valid;

/**
 * Controller for handling user authentication-related operations.
 */
@RestController
@RequestMapping("/api/auth")

public class UserAuthController {
	  @Autowired
	  private UserInterface UserService;

	  @Autowired
	  private JwtInterface jwtService;

	  @Autowired
	  private UserAuthResponse userAuthResponse;

	  @Autowired
	  private EntityAndDtoCreation entityAndDtoCreation;
	  
	  

	  /**
	  * Point de terminaison pour l'enregistrement d'un nouveau compte utilisateur.
	  * @param registerRequest La charge utile de la requête contenant les données d'enregistrement.
	  * @param bindingResult Le résultat de la validation de la charge utile de la requête.
	  * @return ResponseEntity contenant la réponse à la demande d'enregistrement.
	  */
	  @PostMapping("/register")
	  public ResponseEntity<?> registerAccount(
	    @Valid @RequestBody RegisterRequest registerRequest,
	    BindingResult bindingResult
	  ) {
	    try {
	      // Vérifier si la charge utile de la requête n'est pas valide
	      boolean isRequestPayloadInvalid = bindingResult.hasErrors();
	      if (isRequestPayloadInvalid) {
	    	 
	        return ResponseEntity
	          .badRequest()
	          .body(
	        		  userAuthResponse.getRegisteringBadCredentialsResponseMessage()
	        		 
	          );
	        
	      }

	      // Vérifiez si l'e-mail est déjà enregistré
	      boolean isEmailAlreadyRegistered = UserService.isEmailAlreadyTaken(
	        registerRequest.getEmail()
	      );
	      if (isEmailAlreadyRegistered) {
	        return ResponseEntity
	          .badRequest()
	          .body(
	        		  userAuthResponse.getRegisteringEmailAlreadyUsedResponseMessage()
	          );
	      }

	      // Vérifiez si Username est déjà enregistré
	      boolean isUsernameAlreadyTaken = UserService.isUsernameAlreadyTaken(
	        registerRequest.getUsername()
	      );
	      if (isUsernameAlreadyTaken) {
	        return ResponseEntity
	          .badRequest()
	          .body(
	        		  userAuthResponse.getRegisterUserameAlreadyUsedResponseMessage()
	          );
	      }

	      // Creation et Enregistre User
	      User newUser = entityAndDtoCreation.getDbUserFromRegisterRequest(
	        registerRequest
	      );
	      UserService.saveUser(newUser);

	      return ResponseEntity.ok().build();
	    } catch (Exception e) {
	      return ResponseEntity.internalServerError().build();
	    }
	  }
	  
	  
	  
	  /**
	  * Point de terminaison pour la connexion de l'utilisateur.
	  * @param loginRequest La charge utile de la requête contenant les identifiants de connexion.
	  * @param bindingResult Le résultat de la validation de la charge utile de la requête.
	  * @return ResponseEntity contenant la réponse à la requête de connexion.
	  */
	  @PostMapping("/login")
	  public ResponseEntity<?> loginAccount(
	    @Valid @RequestBody LoginRequest loginRequest,
	    BindingResult bindingResult
	  ) {
	    try {
	    
	    	// Vérifier si la charge utile de la requête n'est pas valide
	      boolean isRequestPayloadInvalid = bindingResult.hasErrors();
	    	    
	      if (isRequestPayloadInvalid) {
	        return ResponseEntity
	          .badRequest()
	          .body(userAuthResponse.getLoginBadCredentialsResponseMessage());
	      }

	   // Vérifier si l'utilisateur existe
	      User dbUser = doesUserExist(loginRequest.getEmailOrUsername());
         
	      if (dbUser == null) {
	        return ResponseEntity
	          .badRequest()
	          .body(userAuthResponse.getLoginBadCredentialsResponseMessage());
	      }
         System.out.println("alo");
	      // verifier si le password correct
	      boolean isPasswordCorrect = UserService.isPasswordValid(
	        loginRequest.getPassword(),
	        dbUser
	      );
	      
	      if (!isPasswordCorrect) {
	        return ResponseEntity
	          .badRequest()
	          .body(userAuthResponse.getLoginBadCredentialsResponseMessage());
	      }
	      
	      // Generation JWT token
	      String jwtToken = jwtService.generateToken(dbUser.getId());
	      

	      return ResponseEntity
	        .ok()
	        .body(userAuthResponse.getJwtTokenResponseMessage(jwtToken));
	    } catch (Exception e) {
	      return ResponseEntity.internalServerError().build();
	    }
	  }

	  /**
	  * Méthode d'assistance pour vérifier l'existence d'un utilisateur en fonction de son adresse e-mail ou de son nom d'utilisateur.
	  * @param emailOrUsername : l'adresse e-mail ou le nom d'utilisateur fourni dans la demande de connexion.
	  * @return : l'utilisateur s'il est trouvé, sinon : null.
	  */
	  private User doesUserExist(String emailOrUsername) {
	    Optional<User> optionalDbUserByEmail = UserService.getUserByEmail(
	      emailOrUsername
	    );
	    Optional<User> optionalDbUserByUsername = UserService.getUserByUsername(
	      emailOrUsername
	    );

	    if (optionalDbUserByEmail.isPresent()) {
	      return optionalDbUserByEmail.get();
	    }

	    if (optionalDbUserByUsername.isPresent()) {
	      return optionalDbUserByUsername.get();
	    }

	    return null;
	  }
	  

}
