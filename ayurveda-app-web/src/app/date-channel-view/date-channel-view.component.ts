import {Component, OnInit} from '@angular/core';
import {HttpClientService} from "../service/http-client.service";
import {Router} from "@angular/router";
import {DoctorResponse} from "../service/ayurveda-service.mappings";

@Component({
  selector: 'app-date-channel-view',
  templateUrl: './date-channel-view.component.html',
  styleUrls: ['./date-channel-view.component.css']
})
export class DateChannelViewComponent implements OnInit {

  doctorData: DoctorResponse;

  constructor(private httpClientService: HttpClientService, private router: Router) {
    if (this.router.getCurrentNavigation().extras.state) {
      this.doctorData = this.router.getCurrentNavigation().extras.state.response;
    }
    console.log(this.doctorData);
  }

  ngOnInit() {
  }

  getPatientDetailsForChannel(timeSlot) {
    this.router.navigate(['patient-details-fill'],
      {state: {"timeSlot": timeSlot, "doctorResponse": this.doctorData}}).then((e) => {
      if (e) {
        console.log("Navigation is successful!");
      } else {
        console.log("Navigation has failed!");
      }
    });

  }

}
