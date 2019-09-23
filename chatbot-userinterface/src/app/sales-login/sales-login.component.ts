import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {NgForm } from '@angular/forms';
import { UserLogin } from '../shared/UserLogin';
import { DataService } from '../shared/data.service';
import { HttpClientService } from '../http-client.service';

@Component({
  selector: 'app-sales-login',
  templateUrl: './sales-login.component.html',
  styleUrls: ['./sales-login.component.css']
})
export class SalesLoginComponent implements OnInit {
      userlogin:UserLogin={
    email:"",
    password:""
  };
  customerForm: NgForm;
  constructor(private router: Router, 
    private route: ActivatedRoute,
    private httpclientservice:HttpClientService) { }

  ngOnInit() {
  }

 
    submit() {
      // this.httpclientservice.getCustomer(this.userlogin.email).subscribe((userloging:UserLogin)=>{this.userLogin=userloging;});
       // console.log(this.userLogin.email);
        this.router.navigate(['select']);
    }
} 
 
