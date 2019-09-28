import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../service/authentication.service";

@Component({
  selector : 'app-header',
  templateUrl : './header.component.html'
})
export  class HeaderComponent implements OnInit{
  constructor(private loginService:AuthenticationService){}

  ngOnInit() {

  }
}

