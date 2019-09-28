import { Component, OnInit } from '@angular/core';
import {Consultation} from "../service/ayurveda-service.mappings";
import {HttpClientService} from "../service/http-client.service";

@Component({
  selector: 'app-cashier-screen',
  templateUrl: './cashier-screen.component.html',
  styleUrls: ['./cashier-screen.component.css']
})
export class CashierScreenComponent implements OnInit {
  consultations: Consultation[];
  selectedConsultation: Consultation;
  timeLeft: number = 60;
  interval;

  constructor(private httpClientService: HttpClientService) {
    this.startTimer();
  }


  ngOnInit() {
    this.getAllConsultations();
  }

  startTimer() {
    this.interval = setInterval(() => {
      if (this.timeLeft > 0) {
        this.timeLeft--;
      } else {
        this.getAllConsultations();
        this.timeLeft = 60;
      }
    }, 1000)
  }

  getAllConsultations() {
    this.httpClientService.getAllConsultationsForCashier().subscribe(
      response => {
        if (response) {
          this.consultations = response;
        } else {
          alert('No Consultations found');
        }
      }, (error) => {
        console.log('error occurred', error);
      });
  }

  select(consultation: Consultation) {
    this.selectedConsultation = consultation;
  }

  chargeAndClose() {
    this.httpClientService.chargeAndClose(this.selectedConsultation.id).subscribe(
      response => {
        if (response) {
          this.selectedConsultation = null;
          this.getAllConsultations();
        } else {
          alert('Error Occurred while updating the system');
        }
      }, (error) => {
        console.log('error occurred', error);
      });
  }

  cancelSelection() {
    this.selectedConsultation = null;
  }
}
