import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { HttpClientService } from '../http-client.service';
import { Products } from '../products';
import { ProductResult } from '../ProductResult';

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.css']
})
export class ResultComponent implements OnInit {
  
  products : ProductResult[] = [];
  

  constructor(private router: Router,
    private route: ActivatedRoute,
    private httpClientService : HttpClientService) { }

  ngOnInit() {
    this.route.params.subscribe((params: Params) => {
      let name = params['name'];
      console.log(name);
      if (name !== null) {
            this.getResult(name);
      }
    });
  }   
    
  

  getResult(name:string){
    this.httpClientService.getResult(name).subscribe((response) => {
      this.products=response;
    console.log(this.products);  
    });
  }

  

}
