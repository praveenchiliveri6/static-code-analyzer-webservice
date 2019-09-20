import { Directive, ElementRef, Renderer2, HostListener } from '@angular/core';

@Directive({
  selector: '[appHover]'
})
export class HoverDirective {

  constructor(private el: ElementRef,private renderer:Renderer2) { }
  border : string;
  @HostListener("mouseover")
  onMouseOver(){
    let elem = this.el.nativeElement.querySelector('.productImage');
    this.renderer.setStyle(elem,'display','zoom');
    this.border="5px solid blue"
    

  }

}
