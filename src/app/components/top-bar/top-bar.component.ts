import {
  Component,
  ComponentFactoryResolver,
  ViewContainerRef,
  ViewChild,
  OnInit,
} from '@angular/core';

import { ListComponent } from '../list/list.component';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.scss'],
})
export class TopBarComponent implements OnInit {
  newList: any[] = [];
  @ViewChild('listComponent', { read: ViewContainerRef })
  viewContainerRef!: ViewContainerRef;
  constructor(private componentFactoryResolver: ComponentFactoryResolver) {}

  addNewList() {
    const componentFactory =
      this.componentFactoryResolver.resolveComponentFactory(ListComponent);
    const componentRef = componentFactory.create(
      this.viewContainerRef.injector
    );
    this.newList.push(componentRef);
    console.log('created new list');
  }

  ngOnInit() {}
}
