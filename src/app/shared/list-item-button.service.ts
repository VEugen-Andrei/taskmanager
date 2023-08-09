import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ListItemButtonService {
  private buttonEventSubject = new Subject<void>();
  buttonEvent$ = this.buttonEventSubject.asObservable();

  listItemButtonEvent() {
    this.buttonEventSubject.next();
  }
}
