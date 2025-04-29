package com.openclassrooms.mddapi.util.payload.Response;


import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * Charge utile de réponse pour les opérations liées à User de la base de données.
 */
@Component
public class UserResponse {

  // Message constants
  private static final String TITRE_MESSAGE = "message";
  private static final String INVALIDE_JWT_TOKEN_ID =
    "aucun utilisateur trouvé avec l'ID de jeton jwt";
  private static final String INVALIDE_UPDATE_PROFIL_REQUEST_PAYLOAD =
    "mauvaises informations d'identification pour la mise à jour du profil";
  private static final String INVALIDE_THEME_ID_PARAMETRE =
    "le paramètre id fourni ne correspond à aucun Theme existant";
  private static final String INVALIDE_TYPE_THEME_ID_PARAMETRE =
    "le paramètre id fourni n'est pas un nombre";
  private static final String USER_ALREADY_SUB_OF_THIS_SUBJECT =
    "user est déjà abonné à ce Theme";
  private static final String USER_NOT_SUB_OF_THIS_SUBJECT =
    "user n'est déjà abonné à ce Theme";

  /**
   * Creates a response message with the provided message.
   * @param message The message to include in the response.
   * @return A map containing the response message.
   */
  public Map<String, String> createResponseMessage(String message) {
    Map<String, String> responseBody = new HashMap<>();
    responseBody.put(TITRE_MESSAGE, message);
    return responseBody;
  }

  // Methods for creating specific response messages

  public Map<String, String> getMeNoUserFoundWithThisJwtTokenId() {
    return createResponseMessage(INVALIDE_JWT_TOKEN_ID);
  }

  public Map<String, String> putUpdateProfilInvalidCredentials() {
    return createResponseMessage(INVALIDE_UPDATE_PROFIL_REQUEST_PAYLOAD);
  }

  public Map<String, String> subscriptionInvalidParameterSubjectIdType() {
    return createResponseMessage(INVALIDE_TYPE_THEME_ID_PARAMETRE);
  }

  public Map<String, String> subscriptionInvalidParameterSubjectId() {
    return createResponseMessage(INVALIDE_THEME_ID_PARAMETRE);
  }

  public Map<String, String> subscriptionUserAlreadySubscribed() {
    return createResponseMessage(USER_ALREADY_SUB_OF_THIS_SUBJECT);
  }

  public Map<String, String> subscriptionUserNotSubscribedToThisSubject() {
    return createResponseMessage(USER_NOT_SUB_OF_THIS_SUBJECT);
  }
}