import {Component, OnDestroy, OnInit} from '@angular/core';
import {CometChatService} from "../../service/comet-chat.service";

@Component({
  selector: 'app-group-view',
  templateUrl: './group-view.component.html',
  styleUrls: ['./group-view.component.css']
})
export class GroupViewComponent implements OnInit, OnDestroy {

  chatUser: string;
  isConnected: boolean = false;

  messages = [];

  listenerId = 'Web_App_Listener_Group_ID';

  get currentUser() {
    return this.chatService.currentUser;
  }

  constructor(private chatService: CometChatService) {
  }

  ngOnInit() {
    this.chatService
      .getPreviousMessages('supergroup')
      .then(messages => (this.messages = messages));
    this.chatService.listenForMessages(this.listenerId, msg => this.messages.push(msg));
  }

  loginToChat() {
    this.chatService.login(this.chatUser)
      .then(value => {
        console.log(value);
        this.isConnected = true;
      });
  }

  ngOnDestroy(): void {
    this.chatService.removeListener(this.listenerId);
  }

  sendMessage(message: string) {
    this.chatService.sendMessage('supergroup', message);
    this.messages.push({
      text: message,
      sender: {uid: this.currentUser.uid}
    });
  }

}
