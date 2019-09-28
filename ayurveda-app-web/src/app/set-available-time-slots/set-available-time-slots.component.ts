import {Component, OnInit} from '@angular/core';
import {AvailableTimeRequest, AvailableTimeSlots, Member, TimeSlot} from "../service/ayurveda-service.mappings";
import {HttpClientService} from "../service/http-client.service";

@Component({
  selector: 'app-set-available-time-slots',
  templateUrl: './set-available-time-slots.component.html',
  styleUrls: ['./set-available-time-slots.component.css']
})
export class SetAvailableTimeSlotsComponent implements OnInit {

  selectedAvailableTime: AvailableTimeRequest = new class implements AvailableTimeRequest {
    date: Date;
    maxPatientCount: number;
    roomNumber: number;
    timeSlotId: string;
  };
  doctor: Member;

  availableTimeSlots: AvailableTimeSlots[];

  timeSlots: TimeSlot[];
  selectedSlotId: string;

  constructor(private httpClientService: HttpClientService) {
    this.httpClientService.getCurrentLoggedInUser().subscribe(value => {
      this.doctor = value;
      this.getAllAvailableSlotsForDoc();
    }, error1 => console.log("error", error1));

    this.httpClientService.getAllTimeSlots()
      .subscribe(value => {
        this.timeSlots = value;
      }, error1 => console.log("error", error1));

  }

  ngOnInit() {
  }

  getAllAvailableSlotsForDoc() {
    this.httpClientService.getAllAvailableSlotsForDoc(this.doctor.userId)
      .subscribe(value => {
        this.availableTimeSlots = value;
      }, error1 => console.log("error", error1));

  }


  addNewTimeSlot() {
    console.log("adding data", this.selectedAvailableTime);
    this.httpClientService.addNewAvailableTime(this.selectedAvailableTime, this.doctor.userId)
      .subscribe(
        response => {
          console.log(response);
          alert("added");
          this.getAllAvailableSlotsForDoc();
          this.selectedSlotId = null;
          this.selectedAvailableTime = new class implements AvailableTimeRequest {
            date: Date;
            maxPatientCount: number;
            roomNumber: number;
            timeSlotId: string;
          }
        }, (error) => {
          console.log('error occurred', error);
        });
  }

  selectSlot(slot: AvailableTimeSlots) {
    this.selectedAvailableTime = new class implements AvailableTimeRequest {
      date: Date = slot.date;
      maxPatientCount: number = slot.maxPatientCount;
      roomNumber: number = slot.roomNumber;
      timeSlotId: string = slot.timeSlot.id;
    };
    this.selectedSlotId = slot.id;
  }

  updateTimeSlot() {
    this.httpClientService.updateAvailableTimeSlot(this.selectedAvailableTime, this.doctor.userId, this.selectedSlotId)
      .subscribe(
        response => {
          console.log(response);
          alert("added");
          this.getAllAvailableSlotsForDoc();
          this.selectedSlotId = null;
          this.selectedAvailableTime = new class implements AvailableTimeRequest {
            date: Date;
            maxPatientCount: number;
            roomNumber: number;
            timeSlotId: string;
          };
        }, (error) => {
          console.log('error occurred', error);
        });
  }

  deleteSlot() {
    this.httpClientService.deleteAvailableTimeSlot(this.doctor.userId, this.selectedSlotId)
      .subscribe(
        response => {
          alert("Deleted");
          this.getAllAvailableSlotsForDoc();
          this.selectedSlotId = null;
          this.selectedAvailableTime = new class implements AvailableTimeRequest {
            date: Date;
            maxPatientCount: number;
            roomNumber: number;
            timeSlotId: string;
          }
        }, (error) => {
          console.log('error occurred', error);
        });

  }
}
