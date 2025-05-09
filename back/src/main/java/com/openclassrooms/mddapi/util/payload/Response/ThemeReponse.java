package com.openclassrooms.mddapi.util.payload.Response;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * Response payload for subject-related operations.
 */
@Component
public class ThemeReponse {

  // Message constants
  private static final String MESSAGE_TITLE = "message";
  private static final String INVALID_SUBJECT_ID_PARAMETER =
    "le paramètre id fourni ne correspond à aucun sujet existant";

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

  /**
  * Crée un message de réponse pour un paramètre d'ID de sujet non valide.
  * @return Une carte contenant le message de réponse.
  */
  public Map<String, String> getSubjectInvalidIdParameter() {
    return createResponseMessage(INVALID_SUBJECT_ID_PARAMETER);
  }
}