import { Component, OnInit } from '@angular/core';
import { HttpClientService } from '../http-client.service';
import { Products } from '../products';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

  products : Products[] = [];

  constructor(
    private httpClientservice : HttpClientService){}

  ngOnInit(){
    this.httpClientservice.getProducts().subscribe(response => this.getProducts(response))
  }

  getProducts(response){
    this.products=response;
  }

}
