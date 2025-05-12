import { Commentaire } from './Commentaire.model';

export class Article {
  constructor(
    public id: number,
    public title: string,
    public content: string,
    public auteurId: number,
    public auteurName: string,
    public themeId: number,
    public themeName: string,
    public createdAt: Date,
    public comments?: Commentaire[]
  ) {}
}
