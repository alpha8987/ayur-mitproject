import {Component, OnInit} from '@angular/core';
import {HttpClientService} from "../service/http-client.service";
import {
  AvailableTimeSlots,
  Consultation,
  DoctorSessionInfo,
  Drug,
  Member,
  Prescription,
  Treatments
} from "../service/ayurveda-service.mappings";

@Component({
  selector: 'app-doctor-prescription',
  templateUrl: './doctor-prescription.component.html',
  styleUrls: ['./doctor-prescription.component.css']
})
export class DoctorPrescriptionComponent implements OnInit {
  panelOpenState = false;

  isSessionStarted: boolean = false;
  isSessionFinished: boolean = false;
  sessionList: DoctorSessionInfo[];
  member: Member;
  currentSession: AvailableTimeSlots;
  consultation: Consultation;
  drugsList: Drug[];
  prescriptionItem: Prescription;

  treatments: Treatments[];

  selectedDrug: Drug;

  constructor(private httpClientService: HttpClientService) {
    this.httpClientService.getCurrentLoggedInUser().subscribe(value => {
      this.member = value;
      this.getSessions();
    }, error1 => console.log("error", error1));
    this.getDrugList();

    httpClientService.getAllTreatments()
      .subscribe(value => {
        this.treatments = value;
      }, error1 => console.log("error", error1));
  }

  ngOnInit() {
    this.prescriptionItem = new class implements Prescription {
      direction: string;
      drug: Drug;
      qty: number;
    }
  }

  getSessions() {
    this.httpClientService.getFutureSessionsForDoctor(this.member.userId).subscribe(
      response => {
        if (response) {
          this.sessionList = response;
        } else {
          alert('No appointments found');
        }
      }, (error) => {
        console.log('error occurred', error);
      });
  }

  startSession(sessionId) {
    this.httpClientService.startSession(sessionId).subscribe(
      response => {
        if (response) {
          this.currentSession = response;
          this.isSessionStarted = true;
          this.getNextAppointment(sessionId);
        } else {
          alert('No Session found');
        }
      }, (error) => {
        console.log('error occurred', error);
      });
  }

  endSession(sessionId) {
    this.httpClientService.endSession(sessionId).subscribe(
      response => {
        alert("Session Ended");
        this.getNextAppointment(sessionId);
      }, (error) => {
        console.log('error occurred', error);
      });
  }

  getNextAppointment(sessionId) {
    this.httpClientService.getNextAppointment(sessionId).subscribe(
      response => {
        if (response) {
          this.consultation = response;
          this.currentSession = response.currentSession;
        } else {
          alert('No Appointments found');
          this.isSessionStarted = false;
          this.isSessionFinished = true;
          this.getSessions();
        }
      }, (error) => {
        console.log('error occurred', error);
      });
  }

  moveToPostConsult() {
    this.httpClientService.moveToPostConsult(this.consultation).subscribe(
      response => {
        if (response) {
          this.getNextAppointment(this.currentSession.id);
        } else {
          alert('Consultation did not closed successfully');
        }
      }, (error) => {
        console.log('error occurred', error);
      });
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

  addInternalPrescriptionItem() {
    if (this.prescriptionItem.drug.availableQuantity < this.prescriptionItem.qty) {
      alert("Moving the item to external prescription as its not available");
      this.addExternalPrescriptionItem();
    } else {
      if (!this.consultation.internalPrescriptionList) {
        this.consultation.internalPrescriptionList = [];
      } else {
        this.consultation.internalPrescriptionList = this.consultation.internalPrescriptionList.filter(value => {
          return value.drug.name != this.prescriptionItem.drug.name;
        });
      }
      console.log("adding new item", this.prescriptionItem);
      this.consultation.internalPrescriptionList.push(this.prescriptionItem);
    }

    this.prescriptionItem = new class implements Prescription {
      direction: string;
      drug: Drug;
      qty: number;
    };
    this.prescriptionItem.drug = this.selectedDrug;
  }

  setSelectedDrug(drug: Drug) {
    console.log("changing drug val", drug);
    this.prescriptionItem.drug = drug;
  }

  removeFromInternalList(prescription: Prescription) {
    var index = this.consultation.internalPrescriptionList.indexOf(prescription);
    this.consultation.internalPrescriptionList.splice(index, 1);
  }

  removeFromExternalList(prescription: Prescription) {
    var index = this.consultation.externalPrescriptionList.indexOf(prescription);
    this.consultation.externalPrescriptionList.splice(index, 1);
  }

  addExternalPrescriptionItem() {
    if (!this.consultation.externalPrescriptionList) {
      this.consultation.externalPrescriptionList = [];
    } else {
      this.consultation.externalPrescriptionList = this.consultation.externalPrescriptionList.filter(value => {
        return value.drug.name != this.prescriptionItem.drug.name;
      });
    }
    this.consultation.externalPrescriptionList.push(this.prescriptionItem);
  }
}
