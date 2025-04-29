package com.openclassrooms.mddapi.util.payload.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Modèle de données pour la demande d'enregistrement des utilisateurs.
 */
@Data
public class RegisterRequest {

  /**
   * Adresse Email Register.
   */
  @NotBlank(message = "An email is required")
  @Email
  private String email;

  /**
   * Le Username de Register.
   */
  @NotBlank(message = "A username is required")
  private String username;

  /**
   * Le mot de passe de Register.
   */
  @NotBlank(message = "A password is required")
  @Pattern(
    regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!£°;@#$%^&*()-+=]).*",
    message = "Le mot de passe doit respecter des critères"
  )
  private String password;
}