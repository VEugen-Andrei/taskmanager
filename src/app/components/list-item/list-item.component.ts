import { Component, Input, ViewChild } from '@angular/core';
import { MatTable, MatTableDataSource } from '@angular/material/table';

export interface TaskTable {
  position: number;
  title: string;
  description: string;
  priority: string;
  status: string;
}

const taskTable: TaskTable[] = [
  {
    position: 0,
    title: 'food',
    description: 'food',
    priority: 'high',
    status: 'done',
  },
  {
    position: 0,
    title: 'food',
    description: 'food',
    priority: 'high',
    status: 'done',
  },
  {
    position: 0,
    title: 'food',
    description: 'food',
    priority: 'high',
    status: 'done',
  },
  {
    position: 0,
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
export class ListItemComponent {
  @Input() taskTable: TaskTable[] = [];
  listContainerTitle = '';

  displayedColumns: string[] = [
    'position',
    'title',
    'description',
    'priority',
    'status',
  ];

  dataSource: TaskTable[] = [];

  constructor() {
    this.dataSource = taskTable;
  }

  @ViewChild(MatTable) table: MatTable<TaskTable> | undefined;
}
