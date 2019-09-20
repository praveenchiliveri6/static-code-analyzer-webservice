import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChatCompComponent } from './chat-comp/chat-comp.component';
import { ChatServiceService } from './chat-service.service';



@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ],
  providers: [ChatServiceService]
})
export class ChatDisplayModule {

 }
