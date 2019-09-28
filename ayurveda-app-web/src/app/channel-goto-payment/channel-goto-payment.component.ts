import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {
  AppointmentResponse,
  AppointmentStatus,
  Doctor,
  Patient,
  TimeSlot,
  UserAuthenticateData
} from "../service/ayurveda-service.mappings";

@Component({
  selector: 'app-channel-goto-payment',
  templateUrl: './channel-goto-payment.component.html',
  styleUrls: ['./channel-goto-payment.component.css']
})
export class ChannelGotoPaymentComponent implements OnInit {

  appointmentData: AppointmentResponse;
  today: number = Date.now();

  constructor(private router: Router) {
    if (this.router.getCurrentNavigation().extras.state) {
      this.appointmentData = this.router.getCurrentNavigation().extras.state.response;
      console.log(this.appointmentData);
    } else {
      alert("Required data was not received");
      this.router.navigate(['home']).catch(reason => console.log("error" + reason));
    }
  }

  ngOnInit() {
    console.log(this.appointmentData);
  }

  executeSubmit() {

  }
}
