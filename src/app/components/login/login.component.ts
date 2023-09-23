import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  form: any = {
    email: null,
    password: null,
  };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';

  private url: string = 'https://localhost:443/api/v1/auth/login';

  constructor(private http: HttpClient) {}

  onSubmit(): void {
    this.http.post(this.url, this.form);
  }
}
