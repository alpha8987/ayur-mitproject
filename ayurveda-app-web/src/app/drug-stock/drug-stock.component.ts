import {Component, OnInit} from '@angular/core';
import {HttpClientService} from "../service/http-client.service";
import {Drug} from "../service/ayurveda-service.mappings";

@Component({
  selector: 'app-drug-stock',
  templateUrl: './drug-stock.component.html',
  styleUrls: ['./drug-stock.component.css']
})
export class DrugStockComponent implements OnInit {

  drugsList: Drug[];
  selectedDrug: Drug = null;

  constructor(private httpClientService: HttpClientService) {
  }

  ngOnInit() {
    this.getDrugList();
  }

  select(drug) {
    this.selectedDrug = drug;
  }


  getDrugList() {
    this.httpClientService.getAllDrugItems().subscribe(
      response => {
        if (response) {
          this.drugsList = response;
        } else {
          alert('No Drugs found');
        }
      }, (error) => {
        console.log('error occurred', error);
      });
  }

  initAddNew() {
    this.selectedDrug = new class implements Drug {
      availableQuantity: number;
      description: string;
      expiryDate: Date;
      id: string;
      manufactureDate: Date;
      name: string;
      reorderLevel: number;
      unitPrice: number;
    }
  }

  addNewDrug() {
    this.httpClientService.addNewDrugItem(this.selectedDrug).subscribe(
      response => {
        if (response) {
          this.selectedDrug = null;
          this.getDrugList();
        } else {
          alert('Error occurred');
        }
      }, (error) => {
        console.log('error occurred', error);
      });
  }

  updateDrugInfo() {
    this.httpClientService.updateNewDrugItem(this.selectedDrug).subscribe(
      response => {
        if (response) {
          this.selectedDrug = null;
          this.getDrugList();
        } else {
          alert('Error occurred');
        }
      }, (error) => {
        console.log('error occurred', error);
      });

  }

  deleteDrug() {
    this.httpClientService.removeDrugItem(this.selectedDrug.id).subscribe(
      response => {
        alert("Drug data deleted successfully. " + response);
        this.getDrugList();
        this.selectedDrug = null;
      }, (error) => {
        console.log('error occurred', error);
      });
  }
}
