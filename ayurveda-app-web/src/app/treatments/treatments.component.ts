import { Component, OnInit } from '@angular/core';
import {HttpClientService} from "../service/http-client.service";
import {Treatments} from "../service/ayurveda-service.mappings";

@Component({
  selector: 'app-treatments',
  templateUrl: './treatments.component.html',
  styleUrls: ['./treatments.component.css']
})
export class TreatmentsComponent implements OnInit {

  typeList:String[];

  searchType: Treatments = new class implements Treatments {
    description: string;
    image: string;
    treatmentId: string;
    type: string;
  };

  searchResults: Treatments[];

  constructor(private httpClientService: HttpClientService) { }

  ngOnInit() {
    this.httpClientService.getAllTreatmentTypes().subscribe(response => {
      console.log("message sent", response);
      this.typeList = response;
    }, (error) => console.log("error occurred", error));

  }


  searchTreatmentData(){
    let keyword = this.searchType.description?this.searchType.description:"";
    this.httpClientService.searchTreatment(this.searchType.type,keyword).subscribe(response => {
           console.log("message sent", response);
           this.searchResults = response;
    }, (error) => console.log("error occurred", error));
  }

}
