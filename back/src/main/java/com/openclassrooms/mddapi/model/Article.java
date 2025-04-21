package com.openclassrooms.mddapi.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Model class representing a post entity.
 */
@Data
@Accessors(chain = true)
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "article")
public class Article {

	/** L'identifiant unique de l'utilisateur. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  /** le titre de l'article. */
  @NotNull
  @Size(max = 60)
  @Column(name = "title")
  private String title;

  /** Le contenu de l'article. */
  @NotNull
  @Size(min = 10)
  @Column(name = "content")
  private String content;

  /** L'utilisateur qui a rédigé le message. */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "auteur_id")
  private User user;

  /** Le Theme auquel appartient le message. */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "theme_id")
  private Theme theme;

  /** la date et heure de quand le post est créer. */
  @NotNull
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  /**La liste des commentaire associés  a un article. */
  @OneToMany(
    mappedBy = "article",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
  )
  private List<Commentaire> comments;
}