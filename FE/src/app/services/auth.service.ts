import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { AppConstants } from '../shared/app.constants';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  login(loginForm: FormGroup): Observable<any> {
    return this.http.post(
      AppConstants.AUTH_API + 'signin',
      {
        email: loginForm.controls['email'],
        password: loginForm.controls['password'],
      },
      AppConstants.httpOptions
    );
  }

  register(registerForm: FormGroup): Observable<any> {
    return this.http.post(
      AppConstants.AUTH_API + 'signup',
      {
        name: registerForm.controls['name'],
        surname: registerForm.controls['surname'],
        email: registerForm.controls['email'],
        password: registerForm.controls['password'],
        //passwordConfirm: registerForm.controls['passwordConfirm'],
        socialProvider: 'LOCAL',
      },
      AppConstants.httpOptions
    );
  }
}
