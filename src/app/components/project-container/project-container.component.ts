import { Component, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { ProjectButtonService } from 'src/app/shared/project-button.service';

@Component({
  selector: 'app-project-container',
  templateUrl: './project-container.component.html',
  styleUrls: ['./project-container.component.scss'],
})
export class ProjectContainerComponent implements OnDestroy {
  projectList: any[] = [];
  private subscription: Subscription;

  constructor(private projectButtonService: ProjectButtonService) {
    this.subscription = this.projectButtonService.buttonEvent$.subscribe(() => {
      this.addProject();
    });
  }

  addProject() {
    this.projectList.push({});
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
