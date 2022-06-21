import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable } from 'rxjs';
import { AppConstants } from '../shared/app.constants';
import { User } from '../shared/user';
import { ProcessHttpMsgService } from './process-http-msg.service';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(
    private http: HttpClient,
    private processHttpMsgService: ProcessHttpMsgService
  ) {}

  getCurrentUser(): Observable<User> {
    return this.http.get<User>(
      `${AppConstants.API_URL}/user/me`,
      AppConstants.HTTP_OPTIONS
    );
  }

  getUsers(): Observable<User[]> {
    return this.http
      .get<User[]>(`${AppConstants.API_URL}/user`)
      .pipe(catchError(this.processHttpMsgService.handleError));
  }

  getUser(email: string): Observable<User> {
    return this.http
      .get<User>(`${AppConstants.API_URL}/user/${email}`)
      .pipe(catchError(this.processHttpMsgService.handleError));
  }

  putUser(user: User): Observable<User> {
    return this.http
      .put<User>(
        `${AppConstants.API_URL}/user/registration`,
        user,
        AppConstants.HTTP_OPTIONS
      )
      .pipe(catchError(this.processHttpMsgService.handleError));
  }
}
