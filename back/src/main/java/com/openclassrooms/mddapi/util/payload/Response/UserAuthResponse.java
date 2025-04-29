package com.openclassrooms.mddapi.util.payload.Response;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * Response payload for database user authentication.
 */
@Component
public class UserAuthResponse {

  // Message constants
  private static final String MESSAGE_TITLE = "message";
  private static final String REGISTERING_BAD_CREDENTIALS =
    "Mauvaises informations Register pour l'inscription";
  private static final String REGISTERING_EMAIL_ALREADY_USE =
    "Email exist déja";
  private static final String REGISTERING_USERNAME_ALREADY_TAKEN =
    "Username exist déja";
  private static final String LOGIN_BAD_CREDENTIALS =
    "Mauvaise Information pour login";
  private static final String LOGIN_ERROR_LOGIN_USER =
    "Une erreur s'est produite lors de la tentative de connexion de User";
  private static final String JWT_INVALID_JWT = "jwt Invalide";
  private static final String TOKEN = "token";
  private static final String USER_INVALID_ID_PARAMETER =
    "Parametre Id user Invalid";
  private static final String USER_ERROR_OCCUR =
    "Une erreur s'est produite lors de la tentative d'obtention de User";

  /**
  * Crée un message de réponse avec le message fourni.
  * @param message Le message à inclure dans la réponse.
  * @return Une carte contenant le message de réponse.
  */
  public Map<String, String> createResponseMessage(String message) {
    Map<String, String> responseBody = new HashMap<>();
    responseBody.put(MESSAGE_TITLE, message);
    return responseBody;
  }

//Méthodes de création de messages de réponse spécifiques

  public Map<String, String> getRegisteringBadCredentialsResponseMessage() {
    return createResponseMessage(REGISTERING_BAD_CREDENTIALS);
  }

  public Map<String, String> getRegisteringEmailAlreadyUsedResponseMessage() {
    return createResponseMessage(REGISTERING_EMAIL_ALREADY_USE);
  }

  public Map<String, String> getRegisterUserameAlreadyUsedResponseMessage() {
    return createResponseMessage(REGISTERING_USERNAME_ALREADY_TAKEN);
  }

  public Map<String, String> getLoginBadCredentialsResponseMessage() {
    return createResponseMessage(LOGIN_BAD_CREDENTIALS);
  }

  public Map<String, String> getLoginErrorResponseMessage() {
    return createResponseMessage(LOGIN_ERROR_LOGIN_USER);
  }

  public Map<String, String> getJwtInvalidJwtResponseMessage() {
    return createResponseMessage(JWT_INVALID_JWT);
  }

  public Map<String, String> getJwtTokenResponseMessage(String jwtToken) {
    Map<String, String> jwtTokenResponse = new HashMap<>();
    jwtTokenResponse.put(TOKEN, jwtToken);
    return jwtTokenResponse;
  }

  public Map<String, String> getUserInvalidIdParameterResponseMessage() {
    return createResponseMessage(USER_INVALID_ID_PARAMETER);
  }

  public Map<String, String> getUserErrorOccurResponseMessage() {
    return createResponseMessage(USER_ERROR_OCCUR);
  }
}