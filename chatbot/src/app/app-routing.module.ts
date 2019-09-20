import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ChatCompComponent } from './chat-display/chat-comp/chat-comp.component'

const routes: Routes = [{path:"api/employees/chat?message=philips&username=ashutosh",
component:ChatCompComponent}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
