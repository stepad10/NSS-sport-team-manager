import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { TokenStorageService } from '../services/token-storage.service';
import { UserService } from '../services/user.service';
import { AppConstants } from '../shared/app.constants';
import { User } from '../shared/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  errorMessage = '';

  googleURL = AppConstants.GOOGLE_AUTH_URL;
  facebookURL = AppConstants.FACEBOOK_AUTH_URL;
  githubURL = AppConstants.GITHUB_AUTH_URL;

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private tokenStorage: TokenStorageService,
    private formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const token = this.route.snapshot.queryParamMap.get('token');
    const error = this.route.snapshot.queryParamMap.get('error');

    if (token) {
      this.userService.getCurrentUser().subscribe({
        next: (data) => {
          this.login(data);
          this.tokenStorage.saveToken(token);
        },
        error: (err) => {
          this.errorMessage = err.error.message;
        },
      });
    } else if (error) {
      this.errorMessage = error;
    }

    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: [
        '',
        [
          Validators.required,
          Validators.minLength(6),
          Validators.maxLength(40),
        ],
      ],
    });
  }

  get f() {
    return this.loginForm.controls;
  }

  onSubmit() {
    console.log(this.loginForm.value);
    this.authService.login(this.loginForm).subscribe({
      next: (data) => {
        this.tokenStorage.saveToken(data.accessToken);
        this.login(data.user);
      },
      error: (err) => {
        this.errorMessage = err.error.message;
      },
    });
  }

  login(user: User) {
    this.tokenStorage.saveUser(user);
    window.location.reload();
    this.router.navigate(['/']);
  }
}
