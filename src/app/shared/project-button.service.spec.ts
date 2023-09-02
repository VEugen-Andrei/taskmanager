import { TestBed } from '@angular/core/testing';

import { ProjectButtonService } from './project-button.service';

describe('ProjectButtonService', () => {
  let service: ProjectButtonService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProjectButtonService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
