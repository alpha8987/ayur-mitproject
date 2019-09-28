import {Component, OnInit} from '@angular/core';
import {TimeSlot} from "../service/ayurveda-service.mappings";
import {HttpClientService} from "../service/http-client.service";

@Component({
  selector: 'app-time-slots',
  templateUrl: './time-slots.component.html',
  styleUrls: ['./time-slots.component.css']
})
export class TimeSlotsComponent implements OnInit {

  timeSlotList: TimeSlot[];

  selectedTimeSlot: TimeSlot;

  startTme: string;
  endTime: string;


  constructor(private httpClientService: HttpClientService) {
  }

  ngOnInit() {
    this.httpClientService.getAllTimeSlots()
      .subscribe(response => {
          this.timeSlotList = response;
          this.selectedTimeSlot = null;
          this.startTme = "";
          this.endTime = "";
        },
        (error) => console.log("error occurred", error));
  }

  addNewTimeSlot() {
    this.selectedTimeSlot = new class implements TimeSlot {
      endTime: Date;
      id: string;
      startTime: Date;
    };

    var date = new Date();
    let hour = this.startTme.slice(0,2);
    let min = this.startTme.slice(-2);
    date.setHours(Number(hour),Number(min));
    this.selectedTimeSlot.startTime = date;
    date = new Date();
    let endHour = this.endTime.slice(0,2);
    let endMin = this.endTime.slice(-2);
    date.setHours(Number(endHour),Number(endMin));
    this.selectedTimeSlot.endTime = date;

    this.httpClientService.addNewTimeSlot(this.selectedTimeSlot)
      .subscribe(response => {
            this.timeSlotList = response;
            this.selectedTimeSlot = null;
            this.startTme = "";
            this.endTime = "";
        },
        (error) => console.log("error occurred", error));
  }

  selectSlot(timeSlot: TimeSlot) {
    this.selectedTimeSlot = timeSlot;
    let startTime:Date = new Date(this.selectedTimeSlot.startTime);
    this.startTme = ("0"+(startTime.getHours())).slice(-2) +("0"+(startTime.getMinutes())).slice(-2) ;
    let endTime1 = new Date(this.selectedTimeSlot.endTime);
    this.endTime = ("0"+(endTime1.getHours())).slice(-2) +("0"+(endTime1.getMinutes())).slice(-2) ;
  }

  updateSelected() {
    var date = new Date();
    let hour = this.startTme.slice(0,2);
    let min = this.startTme.slice(-2);
    date.setHours(Number(hour),Number(min));
    this.selectedTimeSlot.startTime = date;
    date = new Date();
    let endHour = this.endTime.slice(0,2);
    let endMin = this.endTime.slice(-2);
    date.setHours(Number(endHour),Number(endMin));
    this.selectedTimeSlot.endTime = date;

    this.httpClientService.updateTimeSlot(this.selectedTimeSlot)
      .subscribe(response => {
          this.timeSlotList = response;
          this.selectedTimeSlot = null;
          this.startTme = "";
          this.endTime = "";
        },
        (error) => console.log("error occurred", error));

  }

  deleteSelected() {
    this.httpClientService.deleteTimeSlot(this.selectedTimeSlot.id)
      .subscribe(response => {
          this.timeSlotList = response;
          this.selectedTimeSlot = null;
          this.startTme = "";
          this.endTime = "";
        },
        (error) => console.log("error occurred", error));
  }
}
