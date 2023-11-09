import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { LocalStorageService } from 'src/app/services/localstorage.service';

export interface AuthenticationResponse {
  token: string;
  id: number;
}

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

  constructor(
    private authService: AuthenticationService,
    private router: Router,
    private localStorageService: LocalStorageService
  ) {}

  onSubmit(): void {
    this.authService.login(this.form.email, this.form.password).subscribe({
      next: (data: AuthenticationResponse) => {
        console.log(data);
        this.localStorageService.saveUser(data);
        this.router.navigate(['/project']).then(() => window.location.reload());
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
}
