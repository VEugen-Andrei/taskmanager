import { TestBed } from '@angular/core/testing';

import { ListItemButtonService } from './list-item-button.service';

describe('ListItemButtonService', () => {
  let service: ListItemButtonService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ListItemButtonService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
