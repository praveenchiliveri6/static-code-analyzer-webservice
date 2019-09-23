import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserLogin } from './UserLogin';
@Injectable({
  providedIn: 'root'
})
export class DataService {
  customerURI:string ="http://localhost:8085/getcustomer";
  constructor(private http:HttpClient) { }
  getCustomer(email:string):Observable<UserLogin>{
    return this.http.get<UserLogin>(`${this.customerURI}/${email}`);
  }
} 