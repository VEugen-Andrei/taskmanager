import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ApiService } from 'src/app/services/api.service';
import { ProjectButtonService } from 'src/app/shared/project-button.service';

export interface Project {
  id: number;
  title: string;
}

@Component({
  selector: 'app-project-container',
  templateUrl: './project-container.component.html',
  styleUrls: ['./project-container.component.scss'],
})
export class ProjectContainerComponent implements OnDestroy, OnInit {
  index: [] = [];
  projectList: Project[] = [];
  private subscription: Subscription;

  constructor(
    private projectButtonService: ProjectButtonService,
    private apiService: ApiService
  ) {
    this.subscription = this.projectButtonService.buttonEvent$.subscribe(() => {
      const projectRequest = '';
      this.addProject(projectRequest);
    });
  }
  ngOnInit(): void {
    this.apiService.getAllProjects().subscribe((data: Project[]) => {
      this.projectList = [];
      this.projectList = data;
      console.log(this.projectList);
    });
  }

  addProject(projectRequest: any) {
    this.apiService.createProject(projectRequest).subscribe(
      (response) => {
        console.log('Project created successfully:', response);
      },
      (error) => {
        console.error('Error creating project:', error);
      }
    );
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
