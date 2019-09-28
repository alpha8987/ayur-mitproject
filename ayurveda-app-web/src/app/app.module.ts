import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HeaderComponent} from './header/header.component';
import {HomeComponent} from './home/home.component';
import {TreatmentsComponent} from './treatments/treatments.component';
import {DrugLocatorsComponent} from './drug-locators/drug-locators.component';
import {AboutUsComponent} from './about-us/about-us.component';
import {ContactUsComponent} from './contact-us/contact-us.component';
import {ChannelDoctorComponent} from './home/channel-doctor/channel-doctor.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {
  MatButtonModule,
  MatCardModule,
  MatCheckboxModule,
  MatDatepickerModule,
  MatDialogModule,
  MatDividerModule, MatFormFieldModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatNativeDateModule,
  MatRadioModule,
  MatSelectModule, MatSidenav,
} from '@angular/material';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {BrowserModule} from '@angular/platform-browser';
import {FooterComponent} from './footer/footer.component';
import {LoginComponent} from './login/login.component';
import {AppRoutingModule} from './app-routing.module';
import {MemberRegistrationComponent, SnackBarComponent} from './member-registration/member-registration.component';
import {ChangepasswordComponent} from './changepassword/changepassword.component';
import {SearchResultViewComponent} from './search-result-view/search-result-view.component';
import {DateChannelViewComponent} from './date-channel-view/date-channel-view.component';
import {PatientDetailsFillComponent} from './patient-details-fill/patient-details-fill.component';
import {TermsAndConditionsComponent} from './terms-and-conditions/terms-and-conditions.component';
import {DoctorPrescriptionComponent} from './doctor-prescription/doctor-prescription.component';
import {MatExpansionModule} from '@angular/material/expansion';
import {PharmacistScreenComponent} from './pharmacist-screen/pharmacist-screen.component';
import {CashierScreenComponent} from './cashier-screen/cashier-screen.component';
import {ChannelConfirmComponent} from './channel-confirm/channel-confirm.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { ReportLinksViewComponent } from './report-links-view/report-links-view.component';
import { ReportAppDetailsComponent } from './report-app-details/report-app-details.component';
import { ReportTreatmentDetailsComponent } from './report-treatment-details/report-treatment-details.component';
import { ReportPaymentDetailsComponent } from './report-payment-details/report-payment-details.component';
import { ReportMedicineStockDetailsComponent } from './report-medicine-stock-details/report-medicine-stock-details.component';
import { ReportDiagnosisComponent } from './report-diagnosis/report-diagnosis.component';
import { ChannelGotoPaymentComponent } from './channel-goto-payment/channel-goto-payment.component';
import { ChannelPaymentSummaryComponent } from './channel-payment-summary/channel-payment-summary.component';
import {NgxPrintModule} from "ngx-print";
import { LogoutComponent } from './logout/logout.component';
import {AgmCoreModule} from "@agm/core";
import { BasicAuthHtppInterceptorComponent } from './service/basic-auth-htpp-interceptor/basic-auth-htpp-interceptor.component';
import { ContactUsMessagesViewComponent } from './contact-us-messages-view/contact-us-messages-view.component';
import { DetailDialogComponent } from './contact-us-messages-view/detail-dialog/detail-dialog.component';
import { TreatmentsAddComponent } from './treatments-add/treatments-add.component';
import {FileUploadModule} from "ng2-file-upload";
import { DrugStockComponent } from './drug-stock/drug-stock.component';
import {NgChatModule} from "ng-chat";
import { GroupViewComponent } from './components/group-view/group-view.component';
import { SetAvailableTimeSlotsComponent } from './set-available-time-slots/set-available-time-slots.component';
import { TimeSlotsComponent } from './time-slots/time-slots.component';
import {IConfig, NgxMaskModule} from 'ngx-mask';

// @ts-ignore
export const options: Partial<IConfig> | (() => Partial<IConfig>);



@NgModule({
  entryComponents:[DetailDialogComponent,SnackBarComponent],
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    TreatmentsComponent,
    DrugLocatorsComponent,
    AboutUsComponent,
    ContactUsComponent,
    ChannelDoctorComponent,
    FooterComponent,
    LoginComponent,
    MemberRegistrationComponent,
    ChangepasswordComponent,
    SearchResultViewComponent,
    DateChannelViewComponent,
    PatientDetailsFillComponent,
    TermsAndConditionsComponent,
    DoctorPrescriptionComponent,
    PharmacistScreenComponent,
    CashierScreenComponent,
    ChannelConfirmComponent,
    ReportLinksViewComponent,
    ReportAppDetailsComponent,
    ReportTreatmentDetailsComponent,
    ReportPaymentDetailsComponent,
    ReportMedicineStockDetailsComponent,
    ReportDiagnosisComponent,
    ChannelGotoPaymentComponent,
    ChannelPaymentSummaryComponent,
    LogoutComponent,
    BasicAuthHtppInterceptorComponent,
    ContactUsMessagesViewComponent,
    DetailDialogComponent,
    TreatmentsAddComponent,
    SnackBarComponent,
    DrugStockComponent,
    GroupViewComponent,
    SetAvailableTimeSlotsComponent,
    TimeSlotsComponent,
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    MatDatepickerModule, MatInputModule, MatNativeDateModule,
    MatSelectModule, MatIconModule, MatCheckboxModule,
    MatRadioModule, MatButtonModule, MatGridListModule,
    BrowserAnimationsModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatGridListModule,
    MatDividerModule,
    MatDialogModule,
    MatExpansionModule,
    HttpClientModule,
    MatCardModule,
    MatFormFieldModule,
    NgxPrintModule,
    FileUploadModule,
    MatSnackBarModule,
    AgmCoreModule.forRoot({
      apiKey:'AIzaSyCAas_095wLE_JkfWvDd0om3gNhus3L4Ew',
      libraries: ['places']
    }),
    NgChatModule,
    NgxMaskModule.forRoot(options),

  ],
  providers: [
    {
      provide:HTTP_INTERCEPTORS, useClass:BasicAuthHtppInterceptorComponent, multi:true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
