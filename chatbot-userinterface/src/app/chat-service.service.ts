  
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ChatClass } from './ChatClass';

@Injectable({
  providedIn: 'root'
})
export class ChatServiceService {
  
  chatURI:string="http://localhost:8085/api/employees/chat";
  constructor(private http:HttpClient) { }

  getCustomers(uri:string):Observable<any>
  { 
    return this.http.get<any>(this.chatURI+uri);
  }
}