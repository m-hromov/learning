import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private http: HttpClient) { }

  create(userId: number, giftCertificateId: number): Observable<any> {
    return this.http.post(`${environment.API_BASE_URL}/users/orders`,{
      userId: userId,
      giftCertificateId: giftCertificateId
    })
  }
}
