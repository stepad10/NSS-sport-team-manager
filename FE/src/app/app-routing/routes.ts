import { Routes } from '@angular/router';
import { ErrorPageComponent } from '../error-page/error-page.component';
import { HomeComponent } from '../home/home.component';
import { LoginComponent } from '../login/login.component';
import { AuthGuardService } from '../services/auth-guard.service';
import { RoleGuardService } from '../services/role-guard.service';
import { Role } from '../shared/user';
import { UserDashboardComponent } from '../user-dashboard/user-dashboard.component';
import { RegisterComponent } from '../user/register/register.component';

export const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: 'user',
    canActivate: [RoleGuardService],
    canActivateChild: [RoleGuardService],
    data: {
      expectedRole: Role[Role.USER],
    },
    children: [
      {
        path: 'dashboard',
        component: UserDashboardComponent,
      }
    ],
  },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: '**', component: ErrorPageComponent },
  //{ path: 'dishdetail/:id', component: DishdetailComponent },
];
