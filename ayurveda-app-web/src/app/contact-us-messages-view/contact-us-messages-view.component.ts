import {Component, Inject, OnInit} from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {DetailDialogComponent} from "./detail-dialog/detail-dialog.component";
import {HttpClientService} from "../service/http-client.service";
import {ContactUs} from "../service/ayurveda-service.mappings";

@Component({
  selector: 'app-contact-us-messages-view',
  templateUrl: './contact-us-messages-view.component.html',
  styleUrls: ['./contact-us-messages-view.component.css']
})
export class ContactUsMessagesViewComponent implements OnInit {

  messageList: ContactUs[];

  constructor(public dialog: MatDialog,private httpClientService: HttpClientService) { }

  ngOnInit() {
    this.getAllMessages();
  }

  getAllMessages() {
    this.httpClientService.viewContactAll().subscribe(response => {
      console.log("message sent", response);
      this.messageList = response;
    }, (error) => console.log("error occurred", error));
  }

  openDialog(message): void {
    const dialogRef = this.dialog.open(DetailDialogComponent, {
      width: '250px',data:{userName:message.name,email:message.email,message : message.message}
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

}
