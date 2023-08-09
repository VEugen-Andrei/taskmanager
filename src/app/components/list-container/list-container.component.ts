import { Component, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { ListItemButtonService } from 'src/app/shared/list-item-button.service';

@Component({
  selector: 'app-list-container',
  templateUrl: './list-container.component.html',
  styleUrls: ['./list-container.component.scss'],
})
export class ListContainerComponent implements OnDestroy {
  listItems: any[] = [];
  private subscription: Subscription;

  constructor(private listItemButtonService: ListItemButtonService) {
    this.subscription = this.listItemButtonService.buttonEvent$.subscribe(
      () => {
        this.addGridTile();
      }
    );
  }

  addGridTile() {
    this.listItems.push({});
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
