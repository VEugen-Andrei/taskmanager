import { AfterViewInit, Component, Input, ViewChild } from '@angular/core';
import { MatTable, MatTableDataSource } from '@angular/material/table';
import { MatSort, Sort, MatSortModule } from '@angular/material/sort';

export interface TaskTable {
  position: number;
  title: string;
  description: string;
  priority: string;
  status: string;
}

const taskTable: TaskTable[] = [
  {
    position: 4,
    title: 'food',
    description: 'food',
    priority: 'high',
    status: 'done',
  },
  {
    position: 2,
    title: 'food',
    description: 'food',
    priority: 'low',
    status: 'done',
  },
  {
    position: 3,
    title: 'food',
    description: 'food',
    priority: 'low',
    status: 'done',
  },
  {
    position: 1,
    title: 'food',
    description: 'food',
    priority: 'high',
    status: 'done',
  },
];

@Component({
  selector: 'app-list-item',
  templateUrl: './list-item.component.html',
  styleUrls: ['./list-item.component.scss'],
})
export class ListItemComponent implements AfterViewInit {
  @Input() taskTable: TaskTable[] = [];
  listContainerTitle = '';

  displayedColumns: string[] = [
    'position',
    'title',
    'description',
    'priority',
    'status',
  ];

  dataSource = new MatTableDataSource(taskTable);

  constructor() {}

  @ViewChild(MatTable) table: MatTable<TaskTable> | undefined;
  //@ViewChild(MatSort) sort: MatSort;

  ngAfterViewInit() {
    //this.dataSource.sort = this.sort;
  }
}
