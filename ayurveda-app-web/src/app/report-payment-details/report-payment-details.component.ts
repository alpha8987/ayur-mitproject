import {Component, OnInit} from '@angular/core';
import {HttpClientService} from "../service/http-client.service";
import {Consultation} from "../service/ayurveda-service.mappings";

@Component({
  selector: 'app-report-payment-details',
  templateUrl: './report-payment-details.component.html',
  styleUrls: ['./report-payment-details.component.css']
})
export class ReportPaymentDetailsComponent implements OnInit {

  FromDate: Date;
  ToDate: Date;
  DateFrom: string;
  DateTo: string;

  consultationList: Consultation[];

  constructor(private httpClientService: HttpClientService) {

  }

  ngOnInit() {
  }

  searchPayments() {
    if (this.FromDate) {
      this.DateFrom = this.FromDate.getFullYear() + "-" + ("0" + (this.FromDate.getMonth() + 1)).slice(-2) + "-" + ("0" + (this.FromDate.getDate())).slice(-2);
    } else {
      this.DateFrom = '-1';
    }
    if (this.ToDate) {
      this.DateTo = this.ToDate.getFullYear() + "-" + ("0" + (this.ToDate.getMonth() + 1)).slice(-2) + "-" + ("0" + (this.ToDate.getDate())).slice(-2);
    } else {
      this.DateTo = '-1';
    }


    this.httpClientService.searchConsultations(this.DateFrom, this.DateTo)
      .subscribe(
        response => {
          this.consultationList = response;
        }, (error) => {
          console.log('error occurred', error);
        });
  }

  getAllPayments() {
    this.httpClientService.getAllConsulations()
      .subscribe(
        response => {
          this.consultationList = response;
        }, (error) => {
          console.log('error occurred', error);
        });
  }
}
