export class Security {

  private static _username = 'unknown';
  private static _password = 'unknown';


  static get username(): string {
    return this._username;
  }

  static set username(value: string) {
    this._username = value;
  }

  static get password(): string {
    return this._password;
  }

  static set password(value: string) {
    this._password = value;
  }
}
