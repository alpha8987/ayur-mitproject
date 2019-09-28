import {Component, OnInit} from '@angular/core';
import {Drug} from "../service/ayurveda-service.mappings";
import {HttpClientService} from "../service/http-client.service";

@Component({
  selector: 'app-report-medicine-stock-details',
  templateUrl: './report-medicine-stock-details.component.html',
  styleUrls: ['./report-medicine-stock-details.component.css']
})
export class ReportMedicineStockDetailsComponent implements OnInit {

  drugList: Drug[];
  allDrugItems: Drug[];

  selectedDrugName: string;
  expiryFromDate: Date;
  expiryToDate: Date;
  expiryFrom: string;
  expiryTo: string;


  constructor(private httpClientService: HttpClientService) {
    this.httpClientService.getAllDrugItems()
      .subscribe(response => {
          if (response) {
            this.allDrugItems = response;
          } else {
            alert("There were no drug records");
          }
        },
        (error) => console.log("error occurred", error));
  }

  ngOnInit() {
  }

  getAllDrugs() {
    this.httpClientService.getAllDrugItems()
      .subscribe(response => {
          if (response) {
            this.drugList = response;
          } else {
            alert("There were no drug records");
          }
        },
        (error) => console.log("error occurred", error));
  }

  searchDrugs() {
    this.selectedDrugName = this.selectedDrugName ? this.selectedDrugName : "-1";
    if (this.expiryFromDate) {
      this.expiryFrom = this.expiryFromDate.getFullYear() + "-" + ("0"+(this.expiryFromDate.getMonth() + 1)).slice(-2) + "-" + ("0"+(this.expiryFromDate.getDate())).slice(-2);
    } else {
      this.expiryFrom = '-1';
    }
    if (this.expiryToDate) {
      this.expiryTo = this.expiryToDate.getFullYear() + "-" + ("0"+(this.expiryToDate.getMonth() + 1)).slice(-2) + "-" + ("0"+(this.expiryToDate.getDate())).slice(-2);
    } else {
      this.expiryTo = '-1';
    }


    this.httpClientService.searchDrugItem(this.selectedDrugName,this.expiryFrom,this.expiryTo)
      .subscribe(response => {
          if (response) {
            this.drugList = response;
          } else {
            alert("There were no drug records");
          }
        },
        (error) => console.log("error occurred", error));

  }

}
