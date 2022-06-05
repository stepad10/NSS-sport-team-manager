import { HttpClient } from '@angular/common/http';
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
        email: loginForm.value['email'],
        password: loginForm.value['password'],
      },
      AppConstants.httpOptions
    );
  }

  register(registerForm: FormGroup): Observable<any> {
    return this.http.post(
      AppConstants.AUTH_API + 'signup',
      {
        name: registerForm.value['name'],
        surname: registerForm.value['surname'],
        email: registerForm.value['email'],
        password: registerForm.value['password'],
        //passwordConfirm: registerForm.value['passwordConfirm'],
        socialProvider: 'LOCAL',
      },
      AppConstants.httpOptions
    );
  }
}
