import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticationService} from '../service/authentication.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username = '';
  password = '';
  invalidLogin = false;
  hide = true;

  constructor(private router: Router,
              private loginservice: AuthenticationService) {
  }

  ngOnInit() {
  }

  checkLogin() {
    console.log("In login");
    if (this.loginservice.authenticate(this.username, this.password)) {
      console.log("login success");
      this.router.navigate(['']);
      this.invalidLogin = false;
    } else
      this.invalidLogin = true;
  }

}
