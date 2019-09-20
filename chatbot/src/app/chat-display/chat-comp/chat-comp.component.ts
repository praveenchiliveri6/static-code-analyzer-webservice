import { Component, OnInit } from '@angular/core';
import { ChatClass } from '../ChatClass';
import { ChatServiceService } from '../chat-service.service';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-chat-comp',
  templateUrl: './chat-comp.component.html',
  styleUrls: ['./chat-comp.component.css']
})
export class ChatCompComponent implements OnInit {

  chat:ChatClass;
  constructor(private chatService:ChatServiceService) { }
  message:string;
  username:string;
  messageForm:NgForm;
  uri:string;
  hours:number
  minutes:number
  ngOnInit() {
    
  }
  
  submit():void
  {  var date = new Date();
    this.hours=date.getHours();
    this.minutes=date.getMinutes();
    this.uri="?message="+this.message+"&username="+this.username
    this.chatService.getCustomers(this.uri).subscribe(data=>
      this.chat=data);
  }

}
