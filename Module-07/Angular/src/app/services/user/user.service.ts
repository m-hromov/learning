import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {User} from "../../shared/models/user.model";
import {StorageService} from "../storage-service/storage.service";
import {JwtHelperService} from "@auth0/angular-jwt";

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
const jwtHelper = new JwtHelperService();

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, private storageService: StorageService) { }

  signin(username: string, password: string): Observable<{accessToken: string}> {
    return this.http.post<{accessToken: string}>(`${environment.API_BASE_URL}/users/signin`,{
      username,
      password
    }, httpOptions)
  }

  signup(username: string, password: string): Observable<{accessToken: string}> {
    return this.http.post<{accessToken: string}>(`${environment.API_BASE_URL}/users/signup`,{
      username,
      password
    }, httpOptions)
  }

  signout(): void {
    this.http.post<User>(`${environment.API_BASE_URL}/users/signout`,null, httpOptions)
  }

  getCurrentUser(): Observable<User> {
    return this.http.get<User>(`${environment.API_BASE_URL}/users/current`, httpOptions)
  }

  isAdminUser(): boolean {
    let user = this.storageService.getUser()
    if (user && user.authorities && user.authorities.includes('ADMIN')) {
      return true
    }
    window.alert("Access denied")
    return false
  }
}
