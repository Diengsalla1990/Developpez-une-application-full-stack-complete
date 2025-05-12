export interface CommentResponse {
  id: number;
  content: string;
  auteurId: number;
  auteurName: string;
  createdAt: Date;
  articleId: number;
}
