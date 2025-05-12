
import { Commentaire } from '../../model/Commentaire.model';
import { CommentResponse } from '../interface/comment/response/CommentResponse';

/**
 * Converts a comment DTO (Data Transfer Object) to a Comment model.
 * @param commentDto The comment DTO to convert.
 * @returns The converted Comment model.
 */
const commentDtoToComment = (commentDto: CommentResponse): Commentaire => {
  const { id, content, auteurId, auteurName, createdAt, articleId } = commentDto;
  return new Commentaire(id, content, auteurId, auteurName, articleId, createdAt);
};

export default commentDtoToComment;
