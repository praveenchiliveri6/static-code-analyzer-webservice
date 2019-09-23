import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Products } from './products';
import { Messages } from './messages';
import { Customer } from './customer';
import { ProductResult } from './ProductResult';
import { error } from 'util';
import { UserLogin } from './shared/UserLogin';

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {
  productURI: string = "http://localhost:8085/products";
  resultURI : string = "http://localhost:8085/result";
  customersURI : string = "http://localhost:8085/user/add";
  customerByProductURI:string="http://localhost:8085/customers"
  customerURI:string ="http://localhost:8085/getcustomer";
  getCustomer(email:string):Observable<UserLogin>{
    return this.http.get<UserLogin>(`${this.customerURI}/${email}`);
  }
  constructor(private http:HttpClient) { }

  getProducts():Observable<Products[]>{
    return this.http.get<Products[]>(this.productURI);
  }

  getbyProductName(productname:string):Observable<Customer[]>{
    return this.http.get<Customer[]>(`${this.customerByProductURI}/${productname}`);
  }

  getResult(name: string) : Observable<ProductResult[]>{
    return this.http.get<ProductResult[]>(`${this.resultURI}/${name}`);
  }

 

  putCustomer(customer:Customer){
    console.log(JSON.stringify(customer));
    return this.http.post<Customer>(this.customersURI,customer).subscribe(
     error => (error));
  }

  
}
