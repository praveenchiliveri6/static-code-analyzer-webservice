import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DataService } from '../shared/data.service';
import { Option } from '../shared/option';
import { HttpClientService } from '../http-client.service';
import { Customer } from '../customer';

@Component({
  selector: 'app-select-options',
  templateUrl: './select-options.component.html',
  styleUrls: ['./select-options.component.css']
})
export class SelectOptionsComponent implements OnInit {
  customers:Customer[];
  loginForm: FormGroup;
  errorMessage: string;
  option:Option; 
  constructor(private formBuilder: FormBuilder, 
                private router: Router,private httpclientservice:HttpClientService ) { }
  
    ngOnInit() { 
        this.buildForm();
    }
  
    buildForm() {
        this.loginForm = this.formBuilder.group({
            option:      ['', [ Validators.required  ]]
        });
    }
  
    submit({ value, valid }: { value: Option, valid: boolean }) {
    console.log(value.option);
        this.httpclientservice.getbyProductName(value.option).subscribe((customer)=>this.customers=customer);
    }
  } 