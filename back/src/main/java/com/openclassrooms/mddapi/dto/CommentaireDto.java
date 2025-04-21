package com.openclassrooms.mddapi.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Objet de transfert de données (DTO) représentant un utilisateur de base de données.

 */
@Data
@AllArgsConstructor
public class CommentaireDto {

  /** L'Id unique de commentaire. */
  private long id;

  /** Le contenu de commentaire. */
  private String content;

  /** L'identifiant unique de l'auteur du commentaire. */
  private long auteurId;

  /** Le nom de l'auteur du commentaire */
  private String auteurName;

  /** La date et l'heure de création du commentaire. */
  private LocalDateTime createdAt;

  /** L'identifiant unique de la publication à laquelle appartient le commentaire. */
  private long articleId;
}