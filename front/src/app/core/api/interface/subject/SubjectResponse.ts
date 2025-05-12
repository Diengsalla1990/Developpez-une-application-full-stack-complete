import { Article } from "../../../model/Article.model";


export interface SubjectResponse {
  id: number;
  name: string;
  description: string;
  articleDtos?: Article[];
}
