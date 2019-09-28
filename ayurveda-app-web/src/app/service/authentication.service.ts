import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {User, UserType} from "./ayurveda-service.mappings";


@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private httpClient: HttpClient) {

  }

  authenticate(username, password) {
    const headers = new HttpHeaders({Authorization: 'Basic ' + btoa(username + ':' + password), 'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*'});
    this.httpClient.get<User>('http://api.ayur.ngrok.io/user/validateLogin', {headers}).subscribe(value => {
      sessionStorage.setItem('username', username);
      let authString = 'Basic ' + btoa(username + ':' + password);
      sessionStorage.setItem('basicauth', authString);
      sessionStorage.setItem('isAdmin', String(value.adminUser));
      sessionStorage.setItem('userType', String(value.userType));
      console.log("value returned", value);
    });
    return this.isUserLoggedIn();

  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem('username');
    return !(user === null);
  }

  isAdminUser() {
    let user = sessionStorage.getItem('isAdmin');
    return !(user === null) && (user==="true");
  }

  isDoctorUser() {
    let user = sessionStorage.getItem('userType');
    return !(user === null) && (user==="DOCTOR");
  }

  isPharmacistUser() {
    let user = sessionStorage.getItem('userType');
    return !(user === null) && (user==="PHARMACIST");
  }

  isCashierUser() {
    let user = sessionStorage.getItem('userType');
    return !(user === null) && (user==="CASHIER");
  }

  logOut() {
    sessionStorage.removeItem('username');
    sessionStorage.removeItem('isAdmin');
    sessionStorage.removeItem('basicauth');
    sessionStorage.removeItem('userType');
  }


}

