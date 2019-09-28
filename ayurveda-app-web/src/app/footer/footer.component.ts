import { Component, OnInit } from '@angular/core';
import {environment} from "../../environments/environment";
import {CometChatService} from "../service/comet-chat.service";

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

  constructor(private cometChat: CometChatService) { }

  ngOnInit() {
    this.cometChat.init(environment.cometChat.appId)
  }

}
