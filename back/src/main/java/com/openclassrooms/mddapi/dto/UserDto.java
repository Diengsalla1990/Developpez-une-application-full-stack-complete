package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Objet de transfert de données (DTO) représentant un utilisateur de base de données.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class UserDto {

  /** l'Id Unique de l'utilisateur. */
  @NotNull
  private long id;

  /** l'email de l'utilisateur. */
  @NotNull
  private String email;

  /** Le username de utilisateur. */
  @NotNull
  private String username;

  /** Mot de passe de l'utilisateur. Ce champ est ignoré lors de la sérialisation JSON.*/
  @JsonIgnore
  private String password;

  /** La liste des identifiants de Theme associés à l'utilisateur. */
  private List<Long> themeIds;
}