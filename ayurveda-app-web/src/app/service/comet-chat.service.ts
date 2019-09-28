import {Injectable} from '@angular/core';
import {CometChat, User} from "@cometchat-pro/chat";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CometChatService {

  currentUser = null;

  constructor() {
  }

  init(appId: string = environment.cometChat.appId) {
    CometChat.init(appId).then(
      msg => console.log('Initialized succesfull: ', msg),
      err => console.log('Initialization failed: ', err),
    );
  }

  login(userId: string) {
    return CometChat.login(userId, environment.cometChat.apiKey)
      .then(
        user => {
          console.log('Login succesfull: ', user);
          this.currentUser = user;
        },
        err => console.log('Login failed: ', err),
      );
  }

  getPreviousMessages(groupId: string) {
    const messageRequest = new CometChat.MessagesRequestBuilder()
      .setGUID(groupId)
      .setLimit(100)
      .build();
    return messageRequest.fetchPrevious();
  }

  listenForMessages(listenerId: string, onMessageReceived: (msg: any) => void) {
    CometChat.addMessageListener(
      listenerId,
      new CometChat.MessageListener({
        onTextMessageReceived: onMessageReceived,
        onMediaMessageReceived: _ => undefined
      })
    );
  }


  removeListener(listenerId: string) {
    CometChat.removeMessageListener(listenerId);
  }

  sendMessage(receiverId: string, text: string) {
    const message = new CometChat.TextMessage(
      receiverId,
      text,
      CometChat.MESSAGE_TYPE.TEXT,
      CometChat.RECEIVER_TYPE.GROUP
    );

    return CometChat.sendMessage(message);
  }

}

