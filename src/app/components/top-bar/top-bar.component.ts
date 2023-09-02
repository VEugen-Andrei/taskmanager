import { Component, OnInit } from '@angular/core';
import { ProjectButtonService } from 'src/app/shared/project-button.service';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.scss'],
})
export class TopBarComponent implements OnInit {
  constructor(private projectButtonService: ProjectButtonService) {}

  addProject() {
    this.projectButtonService.projectButtonEvent();
  }

  ngOnInit() {}
}
