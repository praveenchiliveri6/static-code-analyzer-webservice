import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ProductComponent } from './product/product.component';
import {HttpClientModule} from '@angular/common/http';

import { HoverDirective } from './hover.directive';
import { ResultComponent } from './result/result.component';
import { CustomerComponent } from './customer/customer.component';
import { FormsModule } from '@angular/forms';
import { ChatCompComponent } from './chat-comp/chat-comp.component';

@NgModule({
  declarations: [
    AppComponent,
    ProductComponent,
   
    HoverDirective,
    ResultComponent,
    CustomerComponent,
    ChatCompComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
