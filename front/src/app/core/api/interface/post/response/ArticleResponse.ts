import { Commentaire } from '../../../../../model/Commentaire.model';

export interface PostResponse {
  id: number;
  title: string;
  content: string;
  auteurId: number;
  auteurName: string;
  themeId: number;
  themeName: string;
  createdAt: Date;
  commentDtos?: Commentaire[];
}
