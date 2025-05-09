package com.openclassrooms.mddapi.util.payload.Response;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * Response payload for post-related operations.
 */
@Component
public class ArticleReponse {

  // Message constants
  private static final String MESSAGE_TITLE = "message";
  private static final String INVALID_POST_REQUEST = "request invalide" ;
  private static final String INVALID_SUBJECT_ID =
    "aucun Theme trouvé avec cet identifiant ";
  private static final String INVALID_POST_ID = "Aucun message trouvé avec cet identifiant";

  /**
   * Creates a response message with the provided message.
   * @param message The message to include in the response.
   * @return A map containing the response message.
   */
  public Map<String, String> createResponseMessage(String message) {
    Map<String, String> responseBody = new HashMap<>();
    responseBody.put(MESSAGE_TITLE, message);
    return responseBody;
  }

  // Methods for creating specific response messages

  /**
   * Creates a response message for an invalid post request.
   * @return A map containing the response message.
   */
  public Map<String, String> ArticleInvalidePayload() {
    return createResponseMessage(INVALID_POST_REQUEST);
  }

  /**
   * Creates a response message for an invalid subject ID.
   * @return A map containing the response message.
   */
  public Map<String, String> postPostInvalidSubjectId() {
    return createResponseMessage(INVALID_SUBJECT_ID);
  }

  /**
   * Creates a response message for an invalid post ID.
   * @return A map containing the response message.
   */
  public Map<String, String> getPostInvalidPostId() {
    return createResponseMessage(INVALID_POST_ID);
  }
}