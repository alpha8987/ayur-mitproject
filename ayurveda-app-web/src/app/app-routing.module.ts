import {HomeComponent} from './home/home.component';
import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {LoginComponent} from './login/login.component';
import {AboutUsComponent} from './about-us/about-us.component';
import {ContactUsComponent} from './contact-us/contact-us.component';
import {DrugLocatorsComponent} from './drug-locators/drug-locators.component';
import {MemberRegistrationComponent} from './member-registration/member-registration.component';
import {TreatmentsComponent} from './treatments/treatments.component';
import {ChangepasswordComponent} from './changepassword/changepassword.component';
import {SearchResultViewComponent} from './search-result-view/search-result-view.component';
import {DateChannelViewComponent} from './date-channel-view/date-channel-view.component';
import {PatientDetailsFillComponent} from './patient-details-fill/patient-details-fill.component';
import {DoctorPrescriptionComponent} from './doctor-prescription/doctor-prescription.component';
import {PharmacistScreenComponent} from './pharmacist-screen/pharmacist-screen.component';
import {CashierScreenComponent} from './cashier-screen/cashier-screen.component';
import {ChannelConfirmComponent} from './channel-confirm/channel-confirm.component';
import {ReportLinksViewComponent} from "./report-links-view/report-links-view.component";
import {ReportMedicineStockDetailsComponent} from "./report-medicine-stock-details/report-medicine-stock-details.component";
import {ReportPaymentDetailsComponent} from "./report-payment-details/report-payment-details.component";
import {ReportTreatmentDetailsComponent} from "./report-treatment-details/report-treatment-details.component";
import {ReportAppDetailsComponent} from "./report-app-details/report-app-details.component";
import {ReportDiagnosisComponent} from "./report-diagnosis/report-diagnosis.component";
import {ChannelGotoPaymentComponent} from "./channel-goto-payment/channel-goto-payment.component";
import {ChannelPaymentSummaryComponent} from "./channel-payment-summary/channel-payment-summary.component";
import {LogoutComponent} from "./logout/logout.component";
import {AuthGaurdService} from "./service/auth-gaurd.service";
import {ContactUsMessagesViewComponent} from "./contact-us-messages-view/contact-us-messages-view.component";
import {TreatmentsAddComponent} from "./treatments-add/treatments-add.component";
import {DrugStockComponent} from "./drug-stock/drug-stock.component";
import {GroupViewComponent} from "./components/group-view/group-view.component";
import {SetAvailableTimeSlotsComponent} from "./set-available-time-slots/set-available-time-slots.component";
import {AccessDoctor} from "./service/access-doctor";
import {AccessPharmacist} from "./service/access-pharmacist";
import {AccessCashier} from "./service/access-cashier";
import {AccessAdmin} from "./service/access-admin";
import {TimeSlotsComponent} from "./time-slots/time-slots.component";

const appRoutes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'about-us', component: AboutUsComponent},
  {path: 'contact-us', component: ContactUsComponent},
  {path: 'drug-locators', component: DrugLocatorsComponent},
  {path: 'login', component: LoginComponent},
  {path: 'member-registration', component: MemberRegistrationComponent},
  {path: 'treatments', component: TreatmentsComponent},
  {path: 'changepassword', component: ChangepasswordComponent},
  {path: 'search-result-view', component: SearchResultViewComponent},
  {path: 'date-channel-view', component: DateChannelViewComponent},
  {path: 'patient-details-fill', component: PatientDetailsFillComponent},
  {path: 'doctor-prescription', component: DoctorPrescriptionComponent,canActivate:[AccessDoctor]},
  {path: 'pharmacist-screen', component: PharmacistScreenComponent,canActivate:[AccessPharmacist]},
  {path: 'cashier-screen', component: CashierScreenComponent,canActivate:[AccessCashier]},
  {path: 'channel-confirm', component: ChannelConfirmComponent,canActivate:[AuthGaurdService]},
  {path: 'report-links-view', component: ReportLinksViewComponent,canActivate:[AuthGaurdService]},
  {path: 'report-medicine-stock-details', component: ReportMedicineStockDetailsComponent},
  {path: 'report-payment-details', component: ReportPaymentDetailsComponent,canActivate:[AuthGaurdService]},
  {path: 'report-treatment-details', component: ReportTreatmentDetailsComponent,canActivate:[AuthGaurdService]},
  {path: 'report-app-details', component: ReportAppDetailsComponent,canActivate:[AuthGaurdService]},
  {path: 'report-diagnosis', component: ReportDiagnosisComponent,canActivate:[AuthGaurdService]},
  {path: 'channel-goto-payment', component: ChannelGotoPaymentComponent},
  {path: 'channel-payment-summary', component: ChannelPaymentSummaryComponent},
  {path: 'logout', component: LogoutComponent,canActivate:[AuthGaurdService]},
  {path: 'contact-us-messages-view', component: ContactUsMessagesViewComponent},
  {path: 'treatments-add', component: TreatmentsAddComponent},
  {path: 'drug-stock', component: DrugStockComponent},
  {path: 'group-view', component: GroupViewComponent},
  {path: 'set-available-time-slots', component: SetAvailableTimeSlotsComponent, canActivate:[AccessDoctor]},
  {path: 'time-slots', component: TimeSlotsComponent},

];

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'chat', component: GroupViewComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
