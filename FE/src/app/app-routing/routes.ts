import { Routes } from '@angular/router';
import { AppComponent } from '../app.component';
import { HomeComponent } from '../home/home.component';
import { LoginComponent } from '../login/login.component';
import { RegisterComponent } from '../user/register/register.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  //{ path: 'dishdetail/:id', component: DishdetailComponent },
  //{ path: '', redirectTo: '/home', pathMatch: 'full' },
];
