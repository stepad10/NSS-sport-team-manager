import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { TokenStorageService } from './token-storage.service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuardService implements CanActivate {
  constructor(
    public tokenStorageService: TokenStorageService,
    public router: Router
  ) {}

  canActivate(): boolean {
    if (this.tokenStorageService.isAuthenticated()) {
      return true;
    }
    
    this.router.navigate(['login']);
    return false;
  }
}
