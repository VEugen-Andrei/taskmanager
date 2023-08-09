import { Component, OnInit } from '@angular/core';
import { ListItemButtonService } from 'src/app/shared/list-item-button.service';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.scss'],
})
export class TopBarComponent implements OnInit {
  constructor(private listItemButtonService: ListItemButtonService) {}

  addListItem() {
    this.listItemButtonService.listItemButtonEvent();
  }

  ngOnInit() {}
}
