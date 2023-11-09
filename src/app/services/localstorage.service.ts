import { Injectable } from '@angular/core';

const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root',
})
export class LocalStorageService {
  constructor() {}

  public saveUser(user: { token: string; id: number }) {
    localStorage.setItem(USER_KEY, JSON.stringify(user));
    console.log(user);
    console.log(JSON.stringify(user));
  }

  public getUser(): { token: string; id: number } | null {
    const userString = localStorage.getItem(USER_KEY);
    if (userString) {
      return JSON.parse(userString);
    }
    return null;
  }

  public removeUser() {
    localStorage.removeItem(USER_KEY);
  }

  public clearStorage() {
    localStorage.clear();
  }

  public isLoggedIn(): boolean {
    const user = this.getUser();
    if (user) {
      return true;
    }
    return false;
  }
}
