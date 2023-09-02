import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ProjectButtonService {
  private buttonEventSubject = new Subject<void>();
  buttonEvent$ = this.buttonEventSubject.asObservable();

  projectButtonEvent() {
    this.buttonEventSubject.next();
  }
}
