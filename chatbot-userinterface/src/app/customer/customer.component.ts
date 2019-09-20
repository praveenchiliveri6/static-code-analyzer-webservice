import { Component, OnInit } from '@angular/core';
import { Customer } from '../customer';
import { NgForm } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
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
    
  };
  customerForm: NgForm;
  constructor(private router: Router, 
    private route: ActivatedRoute,
    private httpClientService : HttpClientService) { }

  ngOnInit() {
  }

  submit() {
    this.httpClientService.putCustomer(this.customer);
    console.log(this.customer);
    
    //this.router.navigate(['/products']);    
    
      
  }
  cancel(event: Event) {
    event.preventDefault();
    this.router.navigate(['/products']);
  }

}
