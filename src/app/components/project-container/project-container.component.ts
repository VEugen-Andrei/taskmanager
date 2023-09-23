import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
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
  newlyCreatedProject: Project[] = [];
  private subscription: Subscription;

  constructor(
    private projectButtonService: ProjectButtonService,
    private apiService: ApiService,
    private cd: ChangeDetectorRef
  ) {
    this.subscription = this.projectButtonService.buttonEvent$.subscribe(() => {
      this.addProject();
    });
  }
  ngOnInit(): void {
    const userId = 1; // THIS IS HARD CODED. MUST BE MODIFIED AFTER IMPLEMENTING LOGIN !!!!!!!!!!!!!!!!!!!!!! --------------------------------------------------
    this.apiService.getProjectsByUserId(userId).subscribe((data: Project[]) => {
      this.projectList = [];
      this.projectList = data;
      console.log(this.projectList);
    });
  }

  addProject() {
    const newProject: any = {
      title: 'New Project',
      userId: 1, // THIS IS HARD CODED. MUST BE MODIFIED AFTER IMPLEMENTING LOGIN !!!!!!!!!!!!!!!!!!!!!! --------------------------------------------------
    };
    this.newlyCreatedProject.push(newProject);
    this.apiService.createProject(newProject).subscribe(
      (response: Project) => {
        console.log('Project created successfully:', response);
        this.newlyCreatedProject = this.newlyCreatedProject.filter(
          (project) => project.id !== 0
        );
        this.projectList.push(response);
        this.cd.detectChanges();
      },
      (error) => {
        console.error('Error creating project:', error);
        this.newlyCreatedProject = this.newlyCreatedProject.filter(
          (project) => project.id !== 0
        );
      }
    );
  }

  onProjectDeleted(deletedProjectId: number): void {
    this.projectList = this.projectList.filter(
      (project) => project.id !== deletedProjectId
    );
    const indexToRemove = this.newlyCreatedProject.findIndex(
      (project) => project.id === deletedProjectId
    );

    if (indexToRemove !== -1) {
      this.newlyCreatedProject.splice(indexToRemove, 1);
    }
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
