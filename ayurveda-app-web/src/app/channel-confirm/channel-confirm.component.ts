import {Component, OnInit} from '@angular/core';
import {AppointmentResponse} from '../service/ayurveda-service.mappings';
import {HttpClientService} from '../service/http-client.service';

@Component({
  selector: 'app-channel-confirm',
  templateUrl: './channel-confirm.component.html',
  styleUrls: ['./channel-confirm.component.css']
})
export class ChannelConfirmComponent implements OnInit {

  appointmentData: AppointmentResponse;
  refId: string;
  nic: string;
  name: string;

  constructor(private httpClientService: HttpClientService) {
  }

  ngOnInit() {
  }

  searchAppointment() {
    this.httpClientService.searchAppointment(this.refId, this.nic, this.name).subscribe(
      response => {
        if (response) {
          this.appointmentData = response;
        } else {
          alert('No appointments found');
        }
      }, (error) => {
        console.log('error occurred', error);
      });
  }

  confirmPatientWaiting() {
    this.httpClientService.markAsPatientWaiting(this.appointmentData.appointmentId).subscribe(
      response => {
        if (response) {
          alert('appointment confirmed success');
          this.appointmentData = null;
        } else {
          console.log('No appointments found');
        }
      }, (error) => {
        console.log('error occurred', error);
      });
  }

}
