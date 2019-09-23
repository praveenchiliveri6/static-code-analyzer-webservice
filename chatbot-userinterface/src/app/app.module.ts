import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProductComponent } from './product/product.component';
import {HttpClientModule} from '@angular/common/http';

import { HoverDirective } from './hover.directive';
import { ResultComponent } from './result/result.component';
import { CustomerComponent } from './customer/customer.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ChatCompComponent } from './chat-comp/chat-comp.component';
import { CustomerLoginComponent } from './customer-login/customer-login.component';
import { SalesLoginComponent } from './sales-login/sales-login.component';
import { SelectOptionsComponent } from './select-options/select-options.component';



@NgModule({
  declarations: [
    AppComponent,
    ProductComponent,
   
    HoverDirective,
    ResultComponent,
    CustomerComponent,
    ChatCompComponent,
    CustomerLoginComponent,
    SalesLoginComponent,
    SelectOptionsComponent,
    
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
