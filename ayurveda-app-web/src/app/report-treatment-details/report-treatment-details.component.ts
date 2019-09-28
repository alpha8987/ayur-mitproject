import {Component, OnInit} from '@angular/core';
import {Consultation, Treatments} from "../service/ayurveda-service.mappings";
import {HttpClientService} from "../service/http-client.service";

@Component({
  selector: 'app-report-treatment-details',
  templateUrl: './report-treatment-details.component.html',
  styleUrls: ['./report-treatment-details.component.css']
})
export class ReportTreatmentDetailsComponent implements OnInit {

  treatmentList: Treatments[];
  allTreatment: Treatments[];

  selectedTreamentType: string;
  FromDate: Date;
  ToDate: Date;
  DateFrom: string;
  DateTo: string;

  consultationList: Consultation[];


  constructor(private httpClientService: HttpClientService) {
    this.httpClientService.getAllTreatments()
      .subscribe(response => {
          if (response) {
            this.allTreatment = response;
          } else {
            alert("There were no treatment records");
          }
        },
        (error) => console.log("error occurred", error));
  }

  ngOnInit() {
  }

  getAllConsultations() {
    this.httpClientService.getAllConsulations()
      .subscribe(
        response => {
          this.consultationList = response;
        }, (error) => {
          console.log('error occurred', error);
        });
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

    this.selectedTreamentType = this.selectedTreamentType ? this.selectedTreamentType : "-1";


    this.httpClientService.searchConsultationsWithTreatments(this.DateFrom, this.DateTo, this.selectedTreamentType)
      .subscribe(
        response => {
          this.consultationList = response;
        }, (error) => {
          console.log('error occurred', error);
        });
  }

}

