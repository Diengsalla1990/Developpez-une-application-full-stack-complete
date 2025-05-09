export class Commentaire {
    constructor(
      public id: number,
      public content: string,
      public auteurId: number,
      public auteurName: string,
      public postId: number,
      public createdAt: Date
    ) {}
  }