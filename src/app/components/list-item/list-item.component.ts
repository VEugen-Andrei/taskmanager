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
    status: 'pending',
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
    status: 'pending',
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

  dataSource = new MatTableDataSource(taskTable);

  constructor() {}

  @ViewChild(MatTable) table: MatTable<TaskTable> | undefined;
}
