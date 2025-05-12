import { Article } from './Article.model';

export class Theme {

  constructor(
    public id: number,
    public name: string,
    public description: string,
    public article?: Article[]
  ) {}
}