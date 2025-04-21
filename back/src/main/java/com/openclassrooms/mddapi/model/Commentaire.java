package com.openclassrooms.mddapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Model class representing a comment entity.
 */
@Data
@Accessors(chain = true)
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "commentaire")
public class Commentaire {

	/** L'identifiant unique de l'utilisateur. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  /** le contenu du commentaire. */
  @NotNull
  @Column(name = "content")
  private String content;

  /** L'utilisateur qui a rédigé le commentaire. */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "auteur_id")
  private User user;

  /** La date et l'heure de création du commentaire. */
  @NotNull
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  /**l'article auquel appartient le commentaire. */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "article_id")
  private Article article;
}