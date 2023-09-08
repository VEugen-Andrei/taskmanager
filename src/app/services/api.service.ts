import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  private apiUrl = 'http://localhost:9090/';
  constructor(private http: HttpClient) {}

  createProject(data: any): Observable<any> {
    return this.http.post(this.apiUrl + 'project', data);
  }

  deleteProjectById(id: number): Observable<any> {
    return this.http.delete(this.apiUrl + 'project/' + id);
  }

  updateProjectTitle(id: number, title: string): Observable<any> {
    const body = { title: title };
    return this.http.patch(this.apiUrl + 'project/' + id, body);
  }

  getAllProjects(): Observable<any> {
    return this.http.get(this.apiUrl + 'project/all');
  }

  createTask(data: any): Observable<any> {
    return this.http.post(this.apiUrl + 'task', data);
  }

  deleteTaskById(id: number): Observable<any> {
    return this.http.delete(this.apiUrl + 'task/' + id);
  }

  showTasksByProjectId(projectId: number) {
    return this.http.get(this.apiUrl + 'task?projectId=' + projectId);
  }
}
