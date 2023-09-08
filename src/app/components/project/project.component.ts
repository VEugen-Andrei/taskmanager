import { HttpClient } from '@angular/common/http';
import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
} from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ApiService } from 'src/app/services/api.service';

export interface Project {
  id: number;
  title: string;
}

export interface Task {
  id: number;
  title: string;
  description: string;
  priority: string;
  status: string;
  isEdit: boolean;
  projectId: number;
}

const COLUMN_TABLE = [
  {
    key: 'title',
    type: 'text',
    label: 'Title',
  },
  {
    key: 'description',
    type: 'text',
    label: 'Description',
  },
  {
    key: 'priority',
    type: 'dropdown',
    label: 'Priority',
    options: ['HIGH', 'MEDIUM', 'LOW'],
  },
  {
    key: 'status',
    type: 'dropdown',
    label: 'Status',
    options: ['PENDING', 'DONE'],
  },
  {
    key: 'isEdit',
    type: 'isEdit',
    label: '',
  },
];

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.scss'],
})
export class ProjectComponent implements OnInit {
  @Input() project!: Project;
  projectTitle: string = '';
  taskList: Task[] = [];
  newlyCreatedTasks: Task[] = [];
  dataSource!: MatTableDataSource<Task>;
  displayedColumns: string[] = COLUMN_TABLE.map((column) => column.key);
  columnTable: any = COLUMN_TABLE;

  isInputReadOnly: boolean = false;
  @Output() projectDeleted = new EventEmitter<number>();

  constructor(
    private apiService: ApiService,
    private httpClient: HttpClient,
    private cdRef: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    if (this.project && this.project.id) {
      this.loadTaskForProject(this.project.id);
      this.projectTitle = this.project.title;
    }
  }

  loadTaskForProject(projectId: number): void {
    this.apiService.showTasksByProjectId(projectId).subscribe(
      (taskData: any) => {
        this.taskList = taskData;
        this.dataSource = new MatTableDataSource<Task>(this.taskList);
      },
      (error) => {
        console.error('Error fetching tasks for the project', error);
      }
    );
  }

  addTask() {
    const newTask: any = {
      projectId: this.project.id,
      title: '',
      description: '',
      priority: 'LOW',
      status: 'PENDING',
    };
    this.newlyCreatedTasks.push(newTask);
    this.apiService.createTask(newTask).subscribe(
      (createdTask: Task) => {
        newTask.id = createdTask.id;
        this.dataSource.data.push(newTask);
        this.dataSource._updateChangeSubscription();
      },
      (error) => {
        console.error('Error creating task:', error);
        this.newlyCreatedTasks = this.newlyCreatedTasks.filter(
          (task) => task !== newTask
        );
      }
    );
  }

  deleteTask(id: number) {
    const taskToRemove = this.dataSource.data.find((task) => task.id === id);
    if (taskToRemove) {
      this.apiService.deleteTaskById(id).subscribe(
        () => {
          this.dataSource.data = this.dataSource.data.filter(
            (task) => task.id !== id
          );
          this.dataSource._updateChangeSubscription();
        },
        (error) => {
          console.error(`Error deleting task with ID ${id}:`, error);
        }
      );
    }
  }

  updateProjectTitleOnEnter() {
    if (this.project && this.project.id) {
      this.apiService
        .updateProjectTitle(this.project.id, this.projectTitle)
        .subscribe(
          () => {
            console.log('Project title updated successfully');
            this.isInputReadOnly = true;
          },
          (error) => {
            console.error('Error updating project title:', error);
          }
        );
    }
  }

  deleteProject(projectId: number): void {
    this.apiService.deleteProjectById(projectId).subscribe(
      () => {
        console.log('Project deleted successfully');
        this.projectDeleted.emit(projectId);
      },
      (error) => {
        console.error('Error deleting the project', error);
      }
    );
  }
}
