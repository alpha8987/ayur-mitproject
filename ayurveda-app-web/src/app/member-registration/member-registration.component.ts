import {Component, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {Member, UserType} from "../service/ayurveda-service.mappings";
import {HttpClientService} from "../service/http-client.service";
import {AuthenticationService} from "../service/authentication.service";
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-member-registration',
  templateUrl: './member-registration.component.html',
  styleUrls: ['./member-registration.component.css']
})
export class MemberRegistrationComponent implements OnInit {
  options: FormGroup;

  member: Member = new class implements Member {
    userId:string;
    address: string;
    consultationCharge: number;
    country: string;
    dob: Date;
    email: string;
    emergencyContactName: string;
    emergencyContactNumber: string;
    firstName: string;
    lastName: string;
    medicalLicense: string;
    memberType: UserType = "PATIENT";
    mobileNo: string;
    nic: string;
    notificationMethod: string;
    password: string;
    specializationList: string[];
    title: string;
  };

  specializationListText: String;
  durationInSeconds = 5;

  constructor(private httpClientService: HttpClientService, private authService: AuthenticationService, private snackBar: MatSnackBar) {

  }

  openSnackBar() {
    this.snackBar.openFromComponent(SnackBarComponent, {
      duration: this.durationInSeconds * 1000, verticalPosition: "top",
    });
  }

  registerMemberData() {
    if (this.specializationListText) {
      this.member.specializationList = this.specializationListText.split(",");
    }
    this.httpClientService.registerMember(this.member).subscribe(
      response => {
        console.log("member registered : ", response);
        this.openSnackBar();
        this.member = new class implements Member {
          userId:string;
          address: string;
          consultationCharge: number;
          country: string;
          dob: Date;
          email: string;
          emergencyContactName: string;
          emergencyContactNumber: string;
          firstName: string;
          lastName: string;
          medicalLicense: string;
          memberType: UserType;
          mobileNo: string;
          nic: string;
          notificationMethod: string;
          password: string;
          specializationList: string[];
          title: string;
        };
      }), (error) => console.log("error occurred", error);
  }

  ngOnInit() {
  }

}

@Component({
  selector: 'snack-bar-component-snack',
  templateUrl: 'snack-bar-component-snack.html',
})
export class SnackBarComponent {
}
