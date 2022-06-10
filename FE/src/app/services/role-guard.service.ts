import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateChild,
  Router,
} from '@angular/router';
import { TokenStorageService } from './token-storage.service';

@Injectable({
  providedIn: 'root',
})
export class RoleGuardService implements CanActivate, CanActivateChild {
  constructor(
    private tokenStorageService: TokenStorageService,
    private router: Router
  ) {}

  canActivateChild(route: ActivatedRouteSnapshot): boolean {
    if (
      this.tokenStorageService.isAuthenticated() &&
      this.tokenStorageService.getUser().role === route.data['expectedRole']
    ) {
      return true;
    }
    this.router.navigate(['']);
    return false;
  }

  canActivate(route: ActivatedRouteSnapshot): boolean {
    return this.canActivateChild(route);
  }
}
