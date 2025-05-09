export class User {
    constructor(
      public id: number,
      public email: string,
      public username: string,
      public themeIds?: number[]
    ) {}
  }