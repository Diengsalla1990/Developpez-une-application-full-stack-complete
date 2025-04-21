package com.openclassrooms.mddapi.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Model class representing a user entity.
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(
  name = "user",
  uniqueConstraints = {
    @UniqueConstraint(columnNames = "email"),
    @UniqueConstraint(columnNames = "username"),
  }
)
public class User {

  /** L'identifiant unique de l'utilisateur. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  /** l'adresse Email de user. */
  @NonNull
  @Size(max = 50)
  @Email
  @Column(name = "email")
  private String email;

  /** le Username de user. */
  @NonNull
  @Size(max = 25)
  @Column(name = "username")
  private String username;

  /** Le password de user. */
  @NonNull
  @Size(min = 8, max = 120)
  @Pattern(
    regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!£°;@#$%^&*()-+=]).*",
    message = "Le mot de passe doit répondre aux critères spécifiés."
  )
  @Column(name = "password")
  private String password;

  /** La liste des articles rédigés par l'utilisateur. */
  @OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
  )
  private List<Article> articles;

  /** La liste des commentaires rédigés par l'utilisateur. */
  @OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
  )
  private List<Commentaire> comments;

  /** La liste des Themes associés à l'utilisateur. */
  @ManyToMany
  @JoinTable(
    name = "user_theme",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "theme_id")
  )
  private List<Theme> themes;


}