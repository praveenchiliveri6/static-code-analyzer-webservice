import { Component, OnInit } from '@angular/core';
import { Customer } from '../customer';
import { NgForm } from '@angular/forms';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { HttpClientService } from '../http-client.service';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent implements OnInit {

  customer: Customer = 
  {
    name: "",
    password:"",
    contactNo:"",
    email:"",
    shippingAddress:"",
    productname:""
    
  };
  customerForm: NgForm;
  constructor(private router: Router, 
    private route: ActivatedRoute,
    private httpClientService : HttpClientService) { }

  ngOnInit() {
    this.route.params.subscribe((params: Params) => {
      let name = params['pname'];
      this.customer.productname=name;
      console.log(this.customer.productname);
  });
  }

  submit() {
    this.httpClientService.putCustomer(this.customer);
    //this.router.navigate(['/products']);    
    
      
  }
  cancel(event: Event) {
    event.preventDefault();
    //this.router.navigate(['/products']);
  }

}
