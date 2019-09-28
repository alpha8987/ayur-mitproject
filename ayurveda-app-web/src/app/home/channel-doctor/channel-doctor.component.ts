import {Component, OnInit} from '@angular/core';
import {HttpClientService} from "../../service/http-client.service";
import {
  Doctor,
  DoctorAndSpecializationData,
  DoctorResponse,
  DoctorSearchRequest
} from "../../service/ayurveda-service.mappings";
import {Router} from "@angular/router";

@Component({
  selector: 'app-channel-doctor',
  templateUrl: './channel-doctor.component.html',
  styleUrls: ['./channel-doctor.component.css']
})
export class ChannelDoctorComponent implements OnInit {
  specificationList: string[];
  doctorList: DoctorResponse[];
  doctorIdString: string;
  specialization: string;
  dateSelect: Date;
  searchRequest: DoctorSearchRequest;

  doctorAndSpecializationData: DoctorAndSpecializationData;

  constructor(private httpClientService: HttpClientService, private router: Router) {
    this.searchRequest = new class implements DoctorSearchRequest {
      channelDate: string;
      doctorId: string;
      specialization: string;

    }
  }

  ngOnInit() {
    this.httpClientService.getDoctorAndSpecializationData().subscribe(
      response => this.handleSuccessfulResponse(response),
    );
  }

  handleSuccessfulResponse(response) {
    this.doctorAndSpecializationData = response;
    this.doctorList = this.doctorAndSpecializationData.allDoctors;
    this.specificationList = this.doctorAndSpecializationData.specializationSet;
  }

  searchDoctor() {
    this.searchRequest.doctorId = this.doctorIdString ? this.doctorIdString : '-1';
    this.searchRequest.specialization = this.specialization ? this.specialization : '-1';
    if (this.dateSelect) {
      this.searchRequest.channelDate = this.dateSelect.getFullYear() + "-" + ("0"+(this.dateSelect.getMonth() + 1)).slice(-2) + "-" + ("0"+this.dateSelect.getDate()).slice(-2);
    } else {
      this.searchRequest.channelDate = '';
    }

    console.log(this.searchRequest);

    this.httpClientService.searchDoctor(this.searchRequest)
      .subscribe(response => this.router.navigate(['search-result-view'], {state: {"response": response}}),
        (error) => console.log("error occurred", error));

  };

  // selectChangeHandler(event: any) {
  //   //update the ui
  //   this.tmp = event.target.value;
  //   this.doctorSearch.doctorId = event.target.value;
  // }

}
