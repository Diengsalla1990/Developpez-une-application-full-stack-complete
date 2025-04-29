package com.openclassrooms.mddapi.util.payload.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Modèle de données pour une demande de connexion.
 */
@Data
public class LoginRequest {

  /**
   * Email pour login.
   */
  @NotBlank(message = "Un e-mail ou un nom d'utilisateur est requis pour la connexion")
  private String emailOrUsername;

  /**
   * Password pour login.
   */
  @NotBlank(message = "Un mot de passe est requis pour la connexion")
  private String password;
}