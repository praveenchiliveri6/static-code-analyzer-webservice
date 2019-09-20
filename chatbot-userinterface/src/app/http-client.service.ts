import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Products } from './products';
import { Messages } from './messages';
import { Customer } from './customer';
import { ProductResult } from './ProductResult';
import { error } from 'util';

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {
  customerURI: string = "http://localhost:8085/products";
  resultURI : string = "http://localhost:8085/result";
  customersURI : string = "http://localhost:8085/user/add";

  constructor(private http:HttpClient) { }

  getProducts():Observable<Products[]>{
    return this.http.get<Products[]>(this.customerURI);
  }

  

  getResult(name: string) : Observable<ProductResult[]>{
    return this.http.get<ProductResult[]>(`${this.resultURI}/${name}`);
  }

 

  putCustomer(customer:Customer){
    console.log(JSON.stringify(customer));
    return this.http.post<Customer>(this.customersURI,customer
      
    ).subscribe(error=> console.log('oops',error));
  }
}
