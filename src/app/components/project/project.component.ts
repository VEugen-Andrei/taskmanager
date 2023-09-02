import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatTable, MatTableDataSource } from '@angular/material/table';
import { ApiService } from 'src/app/services/api.service';

export interface Table {
  id: number;
  title: string;
}

export interface TaskTable {
  id: number;
  title: string;
  description: string;
  priority: string;
  status: string;
  task_table_id: number;
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
    options: ['High', 'Medium', 'Low'],
  },
  {
    key: 'status',
    type: 'dropdown',
    label: 'Status',
    options: ['In Progress', 'Done'],
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
  projectContainerTitle = '';
  @Input() taskTableList: TaskTable[] = [];
  @Input() tableSource: any[] = [];
  @ViewChild(MatTable) table!: MatTable<TaskTable>;

  displayedColumns: string[] = COLUMN_TABLE.map((column) => column.key);
  dataSource!: MatTableDataSource<TaskTable>;
  columnTable: any = COLUMN_TABLE;

  constructor(private apiService: ApiService, private httpClient: HttpClient) {}

  ngOnInit(): void {
    this.apiService.getAllProjects().subscribe((data: any) => {
      this.tableSource = data;
    });

    this.apiService.getAllTasks().subscribe((data: TaskTable[]) => {
      this.taskTableList = data;
      this.dataSource = new MatTableDataSource<TaskTable>(this.taskTableList);
    });
  }

  addTask() {
    const newTask = {
      title: '',
      description: '',
      priority: '',
      status: '',
      isEdit: true,
    };
    this.apiService.createTask(newTask).subscribe(
      (createdTask: TaskTable) => {
        this.dataSource.data.unshift(createdTask);
        this.table.renderRows();
      },
      (error) => {
        console.error('Error creating task:', error);
      }
    );
  }

  removeTask(id: any) {
    this.apiService.deleteTaskById(id).subscribe(() => {
      this.dataSource.data = this.dataSource.data.filter(
        (task) => task.id !== id
      );
      this.table.renderRows();
    });
    // http DELETE request spre backend

    // Response response = httpClient.delete("localhost:8080/api/task/" + id)

    // if(Response.status == 200) {
    //   this.dataSource.data = this.dataSource.data.filter(
    //     (task: { id: any }) => task.id !== id
    //   );
    //   else {
    // afisati un mesaj de eroare in frontend
    //   }
    // }
  }
}
