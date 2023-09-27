import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LocalStorageService } from './localstorage.service';

@Injectable({
  providedIn: 'root',
})
export class ApiService {
  private apiUrl = 'https://localhost:443/api';
  constructor(
    private http: HttpClient,
    private localStorage: LocalStorageService
  ) {}

  private getHeaders(token?: string) {
    token = this.localStorage.getUser()?.token;
    if (token) {
      const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`,
      });
      return headers;
    }
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });
    return headers;
  }

  createProject(data: any, token?: string | null): Observable<any> {
    const headers = token ? this.getHeaders(token) : this.getHeaders();
    return this.http.post(this.apiUrl + '/project', data, {
      headers: headers,
    });
  }

  deleteProjectById(id: number, token?: string | null): Observable<any> {
    const headers = token ? this.getHeaders(token) : this.getHeaders();
    return this.http.delete(this.apiUrl + '/project/' + id, {
      headers: headers,
    });
  }

  updateProjectTitle(
    id: number,
    title: string,
    token?: string | null
  ): Observable<any> {
    const body = { title: title };
    const headers = token ? this.getHeaders(token) : this.getHeaders();
    return this.http.patch(this.apiUrl + '/project/' + id, body, {
      headers: headers,
    });
  }

  getProjectsByUserId(userId: number, token?: string | null): Observable<any> {
    const headers = token ? this.getHeaders(token) : this.getHeaders();
    return this.http.get(this.apiUrl + '/project?userId=' + userId, {
      headers: headers,
    });
  }

  createTask(data: any, token?: string | null): Observable<any> {
    const headers = token ? this.getHeaders(token) : this.getHeaders();
    return this.http.post(this.apiUrl + '/task', data, {
      headers: headers,
    });
  }

  deleteTaskById(id: number, token?: string | null): Observable<any> {
    const headers = token ? this.getHeaders(token) : this.getHeaders();
    return this.http.delete(this.apiUrl + '/task/' + id, {
      headers: headers,
    });
  }

  showTasksByProjectId(projectId: number, token?: string | null) {
    const headers = token ? this.getHeaders(token) : this.getHeaders();
    return this.http.get(this.apiUrl + '/task?projectId=' + projectId, {
      headers: headers,
    });
  }

  updateTask(id: number, data: any, token?: string | null) {
    const headers = token ? this.getHeaders(token) : this.getHeaders();
    return this.http.put(this.apiUrl + '/task/' + id, data, {
      headers: headers,
    });
  }
}
