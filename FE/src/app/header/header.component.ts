import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from '../services/token-storage.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  isLoggedIn = false;

  constructor(private tokenStorageService: TokenStorageService, private router: Router) {}

  ngOnInit(): void {
    this.isLoggedIn = this.tokenStorageService.isAuthenticated();
  }

  logout(): void {
    this.tokenStorageService.signOut();
    //window.location.reload();
    this.router.navigate(['']).then(() => {
      window.location.reload();
    });
  }
}
