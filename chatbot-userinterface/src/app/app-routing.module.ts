import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProductComponent } from './product/product.component';

import { ResultComponent } from './result/result.component';
import { CustomerComponent } from './customer/customer.component';
import { ChatCompComponent } from './chat-comp/chat-comp.component';


const routes: Routes = [
  {
    path:'products',
    component:ProductComponent
  },
  {
    path:"api/employees/chat?message=philips&username=ashutosh",
    component:ChatCompComponent
  },
  {
    path: 'result/:name',
    component : ResultComponent
  },
  {
    path: 'products/add',
    component: CustomerComponent
  }
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
