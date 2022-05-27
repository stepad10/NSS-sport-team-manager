import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ProcessHttpMsgService } from './process-http-msg.service';
import { Observable, catchError } from 'rxjs';
import { baseURL } from '../shared/base-url';
import { User } from '../shared/user';

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
      .get<User[]>(baseURL + 'user')
      .pipe(catchError(this.processHttpMsgService.handleError));
  }

  getUser(email: string): Observable<User> {
    return this.http
      .get<User>(`${baseURL}user/${email}`)
      .pipe(catchError(this.processHttpMsgService.handleError));
  }

  putUser(user: User): Observable<User> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
    };

    return this.http
      .put<User>(`${baseURL}user/registration`, user, httpOptions)
      .pipe(catchError(this.processHttpMsgService.handleError));
  }
}
