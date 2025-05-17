import { Routes } from '@angular/router';
import { HomeComponent } from './page/home/home.component';
import { LoginComponent } from './page/login/login.component';
import { RegisterComponent } from './page/register/register.component';
import { ArticlesComponent } from './page/articles/articles.component';
import { isNotAuthGuard } from './guard/is-not-auth.guard';
import { ArticleComponent } from './page/article/article.component';
import { ThemeComponent } from './page/theme/theme.component';
import { NewArticleComponent } from './page/new-article/new-article.component';
import { MeComponent } from './page/me/me.component';
import { isAuthGuard } from './guard/is-auth.guard';

export const routes: Routes = [
     { path: '', component: HomeComponent ,canActivate: [isNotAuthGuard] },
     { path: 'home', component: HomeComponent, canActivate: [isNotAuthGuard] },
  { path: 'login', component: LoginComponent, canActivate: [isNotAuthGuard]},
  {
    path: 'register',
    component: RegisterComponent,
    canActivate: [isNotAuthGuard]
  },

  {
    path: 'articles',
    component: ArticlesComponent,
    canActivate: [isAuthGuard],
  },

  {
    path: 'article/:id',
    component: ArticleComponent,
    canActivate: [isAuthGuard]
  },

  { path: 'theme', component: ThemeComponent,
    canActivate: [isAuthGuard]},

  {
    path: 'new-article',
    component: NewArticleComponent,canActivate: [isAuthGuard]
  },
   { path: 'me', component: MeComponent, canActivate: [isAuthGuard] },
];
