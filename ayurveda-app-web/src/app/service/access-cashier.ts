import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {AuthenticationService} from "./authentication.service";

@Injectable({
  providedIn: 'root'
})
export class AccessCashier implements CanActivate{

  constructor(private router: Router,
              private authService: AuthenticationService) { }


  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (this.authService.isUserLoggedIn() && this.authService.isCashierUser())
      return true;

    alert("Only Cashier can access this");
    this.router.navigate(['login']);
    return false;

  }
}
