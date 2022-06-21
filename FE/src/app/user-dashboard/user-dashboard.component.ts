import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../services/token-storage.service';
import { User } from '../shared/user';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css'],
})
export class UserDashboardComponent implements OnInit {
  
  user?: User;

  constructor(private tokenStorageService: TokenStorageService) {}

  ngOnInit(): void {
    this.user = this.tokenStorageService.getUser();
  }
}
