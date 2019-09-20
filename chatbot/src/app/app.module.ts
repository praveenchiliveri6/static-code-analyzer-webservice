import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {ChatCompComponent} from './chat-display/chat-comp/chat-comp.component'
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { ChatServiceService } from './chat-display/chat-service.service';
import { ChatDisplayModule } from './chat-display/chat-display.module';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    ChatCompComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ChatDisplayModule,
    FormsModule
  ],
  providers: [ChatServiceService],
  bootstrap: [AppComponent]
})
export class AppModule { }
