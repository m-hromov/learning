import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {GiftCertificate} from "../../shared/models/gift-certificate.model";

@Injectable({
  providedIn: 'root'
})
export class GiftCertificateService {
  private apiBaseUrl = environment.API_BASE_URL

  constructor(private http: HttpClient) {
  }

  getCertificates(search: string, page: number): Observable<any> {
    return this.http.get(`${this.apiBaseUrl}/gifts`, {
      params: {
        page: page,
        search: search,
        ascendingCreationDate: true
      }
    })
  }

  getCertificateById(id: number): Observable<any> {
    return this.http.get(`${this.apiBaseUrl}/gifts/${id}`)
  }

  createCertificate(cert: FormData): Observable<any> {
    return this.http.post(`${this.apiBaseUrl}/gifts`, cert)
  }
}
