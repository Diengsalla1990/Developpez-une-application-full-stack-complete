import { Routes } from '@angular/router';
import { HomeComponent } from './page/home/home.component';
import { LoginComponent } from './page/login/login.component';
import { RegisterComponent } from './page/register/register.component';
import { ArticlesComponent } from './page/articles/articles.component';
import { isNotAuthGuard } from './guard/is-not-auth.guard';

export const routes: Routes = [
     { path: '', component: HomeComponent },
     { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent},
  {
    path: 'register',
    component: RegisterComponent
  },

  {
    path: 'articles',
    component: ArticlesComponent
  },
];
