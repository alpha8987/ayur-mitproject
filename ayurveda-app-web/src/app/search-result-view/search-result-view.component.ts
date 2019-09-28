import {Component, OnInit} from '@angular/core';
import {HttpClientService} from "../service/http-client.service";
import {Router} from "@angular/router";
import {DoctorAndSpecializationData, DoctorResponse, DoctorSearchRequest} from "../service/ayurveda-service.mappings";

@Component({
  selector: 'app-channel-date-view',
  templateUrl: './search-result-view.component.html',
  styleUrls: ['./search-result-view.component.css']
})
export class SearchResultViewComponent implements OnInit {

  doctorData: DoctorAndSpecializationData;
  specificationList: string[];
  doctorList: DoctorResponse[];
  doctorIdString: string;
  specialization: string;
  dateSelect: Date;
  searchRequest: DoctorSearchRequest;

  doctorAndSpecializationData: DoctorAndSpecializationData;

  constructor(private httpClientService: HttpClientService, private router: Router) {
    if (this.router.getCurrentNavigation().extras.state) {
      this.doctorData = this.router.getCurrentNavigation().extras.state.response;
    }
    this.searchRequest = new class implements DoctorSearchRequest {
      channelDate: string;
      doctorId: string;
      specialization: string;

    }
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
      .subscribe(response => this.populateSelectedDoctorData(response)), (error) => console.log("error occurred", error);
  };

  populateSelectedDoctorData(response) {
    this.doctorData = response;
  }

  channelDoctor(doctorId) {
    this.httpClientService.getDoctorDataByNic(doctorId)
      .subscribe(response => this.router.navigate(['date-channel-view'],
        {state: {"response": response}}), (error) => console.log(error));
  }

  handleSuccessfulResponse(response) {
    this.doctorAndSpecializationData = response;
    this.doctorList = this.doctorAndSpecializationData.allDoctors;
    this.specificationList = this.doctorAndSpecializationData.specializationSet;
  }

  ngOnInit() {
    this.httpClientService.getDoctorAndSpecializationData().subscribe(
      response => this.handleSuccessfulResponse(response),
    );
  }

}
