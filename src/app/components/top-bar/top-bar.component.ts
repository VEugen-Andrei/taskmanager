import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from 'src/app/services/localstorage.service';
import { ProjectButtonService } from 'src/app/shared/project-button.service';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.scss'],
})
export class TopBarComponent implements OnInit {
  isLoggedIn: boolean = false;

  constructor(
    private projectButtonService: ProjectButtonService,
    private localStorage: LocalStorageService,
    private router: Router
  ) {}

  addProject() {
    this.projectButtonService.projectButtonEvent();
  }

  ngOnInit(): void {
    this.isLoggedIn = this.localStorage.isLoggedIn();
  }

  logout() {
    // this.localStorage.removeUser();
    this.localStorage.clearStorage();
    this.router.navigate(['/login']).then(() => window.location.reload());
  }
}
