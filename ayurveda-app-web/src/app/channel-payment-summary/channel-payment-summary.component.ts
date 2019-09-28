import {Component, OnInit} from '@angular/core';
import {
  AppointmentResponse,
  AppointmentStatus,
  Doctor,
  Patient,
  TimeSlot,
  UserAuthenticateData
} from "../service/ayurveda-service.mappings";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClientService} from "../service/http-client.service";

@Component({
  selector: 'app-channel-payment-summary',
  templateUrl: './channel-payment-summary.component.html',
  styleUrls: ['./channel-payment-summary.component.css']
})
export class ChannelPaymentSummaryComponent implements OnInit {


  appointmentData: AppointmentResponse;

  constructor(private router: Router, private currentRoute: ActivatedRoute, private httpClientService: HttpClientService) {

    console.log(currentRoute.snapshot.queryParams);

    if (this.currentRoute.snapshot.queryParams.order_id) {
      this.getChannelPaymentData(this.currentRoute.snapshot.queryParams.order_id);
      console.log(this.appointmentData);
    } else {
      this.router.navigate(['home']).catch(reason => console.log("error" + reason));
    }
  }

  ngOnInit() {
  }

  getChannelPaymentData(appointmentId) {
    this.httpClientService.getChannelPaymentInfo(appointmentId).subscribe(
      response => {
        console.log("channel payment data : " , response);
        this.appointmentData = response;
      }), (error) => console.log("error occurred", error);

  }

  gotoHome() {
    this.router.navigate(['home']).catch(reason => console.log("error" + reason));
  }
}
