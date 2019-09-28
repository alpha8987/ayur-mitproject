import {Component, OnInit} from '@angular/core';
import {TermsAndConditionsComponent} from '../terms-and-conditions/terms-and-conditions.component';
import {Router} from "@angular/router";
import {AppointmentData, AvailableTimeSlots, DoctorResponse, Member} from "../service/ayurveda-service.mappings";
import {HttpClientService} from "../service/http-client.service";
import {AuthenticationService} from "../service/authentication.service";

@Component({
  selector: 'app-patient-details-fill',
  templateUrl: './patient-details-fill.component.html',
  styleUrls: ['./patient-details-fill.component.css']
})
export class PatientDetailsFillComponent implements OnInit {
  public dialog: TermsAndConditionsComponent;


  doctorData: DoctorResponse;
  timeSlot: AvailableTimeSlots;


  appointmentData: AppointmentData = new class implements AppointmentData {
    acceptedTerms: boolean;
    appointmentDoctorId: string;
    area: string;
    availableTimeSlot: AvailableTimeSlots;
    email: string;
    firstName: string;
    lastName: string;
    local: boolean;
    member: boolean = false;
    mobile: string;
    needDoctorNotification: boolean;
    nic: string;
    paymentMode: string;
    preferredNotificationMethod: string;
    title: string;
  };

  constructor(private httpClientService: HttpClientService, private router: Router,
              private authService: AuthenticationService) {
    if (this.router.getCurrentNavigation().extras.state) {
      this.doctorData = this.router.getCurrentNavigation().extras.state.doctorResponse;
      this.timeSlot = this.router.getCurrentNavigation().extras.state.timeSlot;
    }
    this.appointmentData.member = false;
    this.appointmentData.availableTimeSlot = this.timeSlot;
    this.appointmentData.appointmentDoctorId = this.doctorData.id;
  }

  ngOnInit() {
    if (this.authService.isUserLoggedIn()) {
      this.httpClientService.getCurrentLoggedInUser()
        .subscribe(response => {
          this.appointmentData.firstName = response.firstName;
          this.appointmentData.lastName= response.lastName;
          this.appointmentData.email= response.email;
          this.appointmentData.mobile= response.mobileNo;
          this.appointmentData.nic= response.nic;
          this.appointmentData.preferredNotificationMethod= response.notificationMethod;
          this.appointmentData.member = true;
          this.appointmentData.title = response.title;
          },
          (error) => console.log("error occurred", error));
    }
  }

  openDialog(): void {
    this.dialog.open('test');
  }

  executeChannel() {
    console.log(this.appointmentData);
    this.httpClientService.makeAppointment(this.appointmentData)
      .subscribe(response => this.router.navigate(['channel-goto-payment'], {state: {"response": response}}),
        (error) => console.log("error occurred", error));

  }


}

