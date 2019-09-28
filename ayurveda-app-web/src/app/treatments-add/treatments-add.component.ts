import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpClient, HttpEventType} from "@angular/common/http";
import {FileUploader} from "ng2-file-upload";
import {Treatments} from "../service/ayurveda-service.mappings";
import {HttpClientService} from "../service/http-client.service";

@Component({
  selector: 'app-treatments-add',
  templateUrl: './treatments-add.component.html',
  styleUrls: ['./treatments-add.component.css']
})
export class TreatmentsAddComponent implements OnInit {

  @ViewChild('fileInput') fileInput: ElementRef;

  uploader: FileUploader;
  isDropOver: boolean;

  treatmentData: Treatments = new class implements Treatments {
    description: string;
    image: string;
    treatmentId: string;
    type: string;
  };

  constructor(private httpClientService: HttpClientService) {
  }

  ngOnInit(): void {
    const headers = [{name: 'Accept', value: 'application/json'}];
    this.uploader = new FileUploader({url: 'api/files', autoUpload: true, headers: headers});

    this.uploader.onCompleteAll = () => alert('File uploaded');
  }

  fileOverAnother(e: any): void {
    this.isDropOver = e;
  }

  fileClicked() {
    this.fileInput.nativeElement.click();
  }

  addTreatmentToDB(){
    this.httpClientService.addTreatment(this.treatmentData).subscribe(response => {
      alert("Message Sent Successfully." + response);
      console.log("message sent", response);
    }, (error) => console.log("error occurred", error));
  }

}
