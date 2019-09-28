import { Component, OnInit } from '@angular/core';
import {PasswordResetToken} from "../service/ayurveda-service.mappings";
import {HttpClientService} from "../service/http-client.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-changepassword',
  templateUrl: './changepassword.component.html',
  styleUrls: ['./changepassword.component.css']
})
export class ChangepasswordComponent implements OnInit {

  passwordChange : PasswordResetToken = new class implements PasswordResetToken {
    newPassword: string;
    oldPassword: string;
    userName: string;
  }

  constructor(private httpClientService: HttpClientService, private router: Router) {
  }

  ngOnInit() {
  }

  changePassword() {
    this.httpClientService.passwordChange(this.passwordChange).subscribe(
      response => {
        alert("Successfully Changed the Password")
        this.router.navigate(['login']);
    }, (error) => {
        console.log("error occurred", error);
      });
  }

}
