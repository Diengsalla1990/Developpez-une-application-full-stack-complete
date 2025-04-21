package com.openclassrooms.mddapi.dto;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Objet de transfert de données (DTO) représentant un utilisateur de base de données.

 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ArticleDto {

  /** l'Id unique de l'article. */
  @NotNull
  private long id;

  /** Le Titre de l'article. */
  @NotNull
  private String title;

  /** Le contenu de l'article. */
  @NotNull
  private String content;

  /** l'identifiant unique de l'auteur du message. */
  @NotNull
  private long auteurId;

  /** Le nom de l'auteur de l'article. */
  @NotNull
  private String auteurName;

  /** L'identifiant unique du theme de la publication. */
  @NotNull
  private long themeId;

  /** Le nom du theme de l'article */
  @NotNull
  private String themeName;

  /** La date et l'heure de création de l'article. */
  @NotNull
  private LocalDateTime createdAt;

  /** La liste de commentaire associer a un article .*/
  private List<CommentaireDto> commentDtos;
}