import {Component, Injectable, OnInit} from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})

@Component({
  selector: 'app-basic-auth-htpp-interceptor',
  template:''
})
export class BasicAuthHtppInterceptorComponent implements HttpInterceptor {

  constructor() { }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    console.log("interceptor");

    if (sessionStorage.getItem('username') && sessionStorage.getItem('basicauth')) {
      console.log("in if");
      req = req.clone({
        setHeaders: {
          Authorization: sessionStorage.getItem('basicauth')
        }
      })
    }

    return next.handle(req);

  }


}
