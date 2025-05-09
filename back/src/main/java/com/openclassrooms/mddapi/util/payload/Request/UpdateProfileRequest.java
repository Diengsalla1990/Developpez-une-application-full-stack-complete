package com.openclassrooms.mddapi.util.payload.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Data model for updating a user's profile request.
 */
@Data
public class UpdateProfileRequest {

  /**
   * The updated email of the user.
   * Must not be blank and must be a valid email address.
   */
  @NotBlank(message = "Un email est requis")
  @Email
  private String email;

  /**
   * The updated username of the user.
   * Must not be blank.
   */
  @NotBlank(message = "un username est requis")
  private String username;

  /**
   * The updated password of the user.
   * Must meet certain criteria defined by the specified regular expression.
   */
  @Pattern(
    regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!£°;@#$%^&*()-+=]).*",
    message = "Le mot de passe doit respecter des critères"
  )
  private String password;
}