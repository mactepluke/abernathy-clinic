import { Injectable } from '@angular/core';
import {User} from '../User';
import {Security} from "../security/security";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private _loggedIn = false;

  login(user: User): void {
    Security.username = user.username;
    Security.password = user.password;
    this._loggedIn = true;
  }

  logout(): void {
    let loggedIn = false;
  }

  get currentUser(): User {
    return {username : Security.username, password: Security.password};
  }

  set currentUser(user: User) {
    Security.username = user.username;
    Security.password = user.password;
  }

  get loggedIn(): boolean {
    return this._loggedIn;
  }

  set loggedIn(value: boolean) {
    this._loggedIn = value;
  }
}
