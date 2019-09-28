import { Component, OnInit } from '@angular/core';
import {HttpClientService} from "../service/http-client.service";
import {Appointment, Doctor, DoctorResponse} from "../service/ayurveda-service.mappings";

@Component({
  selector: 'app-report-app-details',
  templateUrl: './report-app-details.component.html',
  styleUrls: ['./report-app-details.component.css']
})
export class ReportAppDetailsComponent implements OnInit {

  appointmentList:Appointment[];
  allDoctors : DoctorResponse[];

  selectedDocId:string;
  FromDate: Date;
  ToDate: Date;
  DateFrom: string;
  DateTo: string;

  constructor(private httpClientService: HttpClientService) {
    this.httpClientService.getDoctorAndSpecializationData()
      .subscribe(response => {
          if (response) {
            this.allDoctors = response.allDoctors;
          } else {
            alert("There were no drug records");
          }
        },
        (error) => console.log("error occurred", error));
  }

  ngOnInit() {
  }


  getAllAppointments() {
    this.httpClientService.getAllAppointments()
      .subscribe(response => {
          if (response) {
            this.appointmentList = response;
          } else {
            alert("There were no drug records");
          }
        },
        (error) => console.log("error occurred", error));
  }

  searchDrugs() {
    this.selectedDocId = this.selectedDocId ? this.selectedDocId : "-1";
    if (this.FromDate) {
      this.DateFrom = this.FromDate.getFullYear() + "-" + ("0"+(this.FromDate.getMonth() + 1)).slice(-2) + "-" + ("0"+(this.FromDate.getDate())).slice(-2);
    } else {
      this.DateFrom = '-1';
    }
    if (this.ToDate) {
      this.DateTo = this.ToDate.getFullYear() + "-" + ("0"+(this.ToDate.getMonth() + 1)).slice(-2) + "-" + ("0"+(this.ToDate.getDate())).slice(-2);
    } else {
      this.DateTo = '-1';
    }


    this.httpClientService.searchAppointment2(this.selectedDocId,this.DateFrom,this.DateTo)
      .subscribe(response => {
          if (response) {
            this.appointmentList = response;
          } else {
            alert("There were no appointment records");
          }
        },
        (error) => console.log("error occurred", error));

  }
}
