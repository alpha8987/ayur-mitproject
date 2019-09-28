import {Component, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {HttpClientService} from "../service/http-client.service";
import {ContactUs} from "../service/ayurveda-service.mappings";

@Component({
  selector: 'app-contact-us',
  templateUrl: './contact-us.component.html',
  styleUrls: ['./contact-us.component.css']
})
export class ContactUsComponent implements OnInit {
  options: FormGroup;

  contactMessage: ContactUs = new class implements ContactUs {
    contactUsId: string;
    email: string;
    message: string;
    name: string;
  };

  constructor(private httpClientService: HttpClientService) {
  }

  ngOnInit() {
  }

  sendMessage() {
    this.httpClientService.addContact(this.contactMessage).subscribe(response => {
      alert("Message Sent Successfully." + response);
      console.log("message sent", response);
    }, (error) => console.log("error occurred", error));
  }

}
