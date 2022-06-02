import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ProcessHttpMsgService } from './process-http-msg.service';
import { Observable, catchError } from 'rxjs';
import { User } from '../shared/user';
import { AppConstants } from '../shared/app.constants';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(
    private http: HttpClient,
    private processHttpMsgService: ProcessHttpMsgService
  ) {}

  getUsers(): Observable<User[]> {
    return this.http
      .get<User[]>(`${AppConstants.API_URL}user`)
      .pipe(catchError(this.processHttpMsgService.handleError));
  }

  getUser(email: string): Observable<User> {
    return this.http
      .get<User>(`${AppConstants.API_URL}user/${email}`)
      .pipe(catchError(this.processHttpMsgService.handleError));
  }

  putUser(user: User): Observable<User> {
    return this.http
      .put<User>(
        `${AppConstants.API_URL}user/registration`,
        user,
        AppConstants.httpOptions
      )
      .pipe(catchError(this.processHttpMsgService.handleError));
  }
}
