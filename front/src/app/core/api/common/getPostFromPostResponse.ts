
import { Article } from '../../model/Article.model';
import { Commentaire } from '../../model/Commentaire.model';
import { CommentResponse } from '../interface/comment/response/CommentResponse';
import { PostResponse } from '../interface/post/response/ArticleResponse';
import commentDtoToComment from './commentDtoToComment';

/**
 * Converts a PostResponse object to a Post object.
 * @param postResponse The PostResponse object to convert.
 * @returns The converted Post object.
 */
const getPostFromPostResponse = (postResponse: PostResponse): Article => {
  if (postResponse.commentDtos) {
    const {
      id,
      title,
      content,
      auteurId,
      auteurName,
      themeId,
      themeName,
      createdAt,
      commentDtos,
    } = postResponse;

    const commentsArr: Commentaire[] = commentDtos.map(
      (commentDto: CommentResponse) => commentDtoToComment(commentDto)
    );

    // Return a new Post object with comments
    return new Article(
      id,
      title,
      content,
      auteurId,
      auteurName,
      themeId,
      themeName,
      createdAt,
      commentsArr
    );
  } else {
    const {
      id,
      title,
      content,
      auteurId,
      auteurName,
      themeId,
      themeName,
      createdAt,
    } = postResponse;

    // Return a new Post object without comments
    return new Article(
      id,
      title,
      content,
      auteurId,
      auteurName,
      themeId,
      themeName,
      createdAt
    );
  }
};

export default getPostFromPostResponse;
