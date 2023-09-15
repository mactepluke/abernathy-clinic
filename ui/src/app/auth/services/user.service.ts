import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {User} from "../User";

@Injectable()
export class UserService {

  constructor(private http: HttpClient) { }

  createUser(user: User): Observable<User> {
    let headers = new HttpHeaders();
    headers = headers.append('Permitted', 'true');
    return this.http.post<User>(`${environment.gateway}/user/create`,
      {
      "username": `${user.username}`,
      "password": `${user.password}`
    },
      {headers});
  }

  loginUser(user: User): Observable<User> {
    let headers = new HttpHeaders();
    headers = headers.append('Permitted', 'true');
    return this.http.post<User>(`${environment.gateway}/user/login`,
      {
        "username": `${user.username}`,
        "password": `${user.password}`
      },
      {headers});
  }


}
