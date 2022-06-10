import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { AppConstants } from '../shared/app.constants';

const SIGN_IN_URL = `/signin`;
const SIGN_UP_URL = `/signup`;
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  login(loginForm: FormGroup): Observable<any> {
    return this.http.post(
      `${AppConstants.AUTH_API}${SIGN_IN_URL}`,
      {
        email: loginForm.value['email'],
        password: loginForm.value['password'],
      },
      AppConstants.HTTP_OPTIONS
    );
  }

  register(registerForm: FormGroup): Observable<any> {
    return this.http.post(
      `${AppConstants.AUTH_API}${SIGN_UP_URL}`,
      {
        name: registerForm.value['name'],
        surname: registerForm.value['surname'],
        email: registerForm.value['email'],
        password: registerForm.value['password'],
        //passwordConfirm: registerForm.value['passwordConfirm'],
        socialProvider: 'LOCAL',
      },
      AppConstants.HTTP_OPTIONS
    );
  }
}
