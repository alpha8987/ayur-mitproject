import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {
  Appointment,
  AppointmentResponse,
  AvailableTimeSlots,
  Consultation,
  ContactUs,
  DoctorAndSpecializationData,
  DoctorResponse,
  DoctorSearchRequest,
  DoctorSessionInfo,
  Drug,
  Member,
  TimeSlot,
  Treatments
} from './ayurveda-service.mappings';

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*'
    })
  };

  constructor(private httpClient: HttpClient) {
  }

  getDoctorAndSpecializationData() {


    return this.httpClient.get<DoctorAndSpecializationData>('http://api.ayur.ngrok.io/doctorAndSpecializationData');
  }

  getDoctorDataByNic(nic) {
    return this.httpClient.get<DoctorResponse>('http://api.ayur.ngrok.io/api/doctors/nic/' + nic);
  }

  public searchDoctor(doctorSearch) {
    return this.httpClient.post<DoctorSearchRequest>('http://api.ayur.ngrok.io/executeDoctorSearch', doctorSearch, this.httpOptions);
  }

  public makeAppointment(appointmentData) {
    return this.httpClient.post<AppointmentResponse>('http://api.ayur.ngrok.io/executeChannel', appointmentData, this.httpOptions);
  }

  getChannelPaymentInfo(appointmentId) {
    return this.httpClient.get<AppointmentResponse>('http://api.ayur.ngrok.io/payment/summary/' + appointmentId, this.httpOptions);
  }

  public addContact(contactMessage) {
    return this.httpClient.post<ContactUs>('http://api.ayur.ngrok.io/api/contact-us/add', contactMessage, this.httpOptions);
  }

  public viewContact(id) {
    return this.httpClient.get<ContactUs>('http://api.ayur.ngrok.io/api/contact-us/find/' + id);
  }

  public viewContactAll() {
    return this.httpClient.get<ContactUs[]>('http://api.ayur.ngrok.io/api/contact-us/findAll');
  }

  public getAllTreatmentTypes() {
    return this.httpClient.get<String[]>('http://api.ayur.ngrok.io/api/treatments/types');
  }

  public getAllTreatments() {
    return this.httpClient.get<Treatments[]>('http://api.ayur.ngrok.io/api/treatments/all');
  }

  public getAllTreatmentTypes2() {
    return this.httpClient.get<Treatments[]>('http://localhost:9090/api/treatments/types');
  }

  public addTreatment(treatment) {
    return this.httpClient.post<Treatments>('http://api.ayur.ngrok.io/api/treatments/add', treatment, this.httpOptions);
  }

  public searchTreatment(type, keyword) {
    return this.httpClient.get<Treatments[]>('http://api.ayur.ngrok.io/api/treatments/search/' + type + '/' + keyword);
  }

  public registerMember(memberData) {
    return this.httpClient.post<Member>('http://api.ayur.ngrok.io/member/register', memberData, this.httpOptions);
  }

  public passwordChange(password) {
    return this.httpClient.post<null>('http://api.ayur.ngrok.io/api/change/password', password, this.httpOptions);
  }

  public searchAppointment(refNo, nic, name) {
    refNo = refNo ? refNo : '-';
    nic = nic ? nic : '-';
    name = name ? name : '-';
    return this.httpClient.get<AppointmentResponse>('http://api.ayur.ngrok.io/api/appointments/find/' + refNo + '/' + nic + '/' + name);
  }

  public markAsPatientWaiting(appointmentId) {
    return this.httpClient.get<boolean>('http://api.ayur.ngrok.io/api/appointments/mark-waiting/' + appointmentId);
  }

  public getCurrentLoggedInUser() {
    return this.httpClient.get<Member>('http://api.ayur.ngrok.io/user/get');
  }

  getFutureSessionsForDoctor(doctorId) {
    return this.httpClient.get<DoctorSessionInfo[]>('http://api.ayur.ngrok.io/api/doctors/sessions/' + doctorId);
  }

  startSession(sessionId) {
    return this.httpClient.get<AvailableTimeSlots>('http://api.ayur.ngrok.io/api/doctors/session/start/' + sessionId);
  }

  endSession(sessionId) {
    return this.httpClient.get<AvailableTimeSlots>('http://api.ayur.ngrok.io/api/doctors/session/end/' + sessionId);
  }

  getNextAppointment(sessionId) {
    return this.httpClient.get<Consultation>('http://api.ayur.ngrok.io/api/appointments/next/' + sessionId);
  }

  moveToPostConsult(consultation) {
    return this.httpClient.post<Consultation>('http://api.ayur.ngrok.io/api/appointments/post-consult', consultation, this.httpOptions);
  }

  public getAllDrugItems() {
    return this.httpClient.get<Drug[]>('http://api.ayur.ngrok.io/api/drug/all');
  }

  public addNewDrugItem(drugItem) {
    return this.httpClient.post<Drug>('http://api.ayur.ngrok.io/api/drug/add', drugItem, this.httpOptions);
  }

  public updateNewDrugItem(drugItem) {
    return this.httpClient.post<Drug>('http://api.ayur.ngrok.io/api/drug/update', drugItem, this.httpOptions);
  }

  public removeDrugItem(drugId) {
    return this.httpClient.get<boolean>('http://api.ayur.ngrok.io/api/drug/delete/' + drugId);
  }

  public searchDrugItem(drugName, expFrom, expTo) {
    return this.httpClient.get<Drug[]>('http://api.ayur.ngrok.io/api/drug/search/' + drugName + '/' + expFrom + '/' + expTo);
  }

  public getDrugItem(drugId) {
    return this.httpClient.get<Drug>('http://api.ayur.ngrok.io/api/drug/get/' + drugId);
  }


  public getAllConsultationsForPharmacy() {
    return this.httpClient.get<Consultation[]>('http://api.ayur.ngrok.io/api/pharmacy/consultations');
  }

  public issueMedicine(consultationId) {
    return this.httpClient.get<boolean>('http://api.ayur.ngrok.io/api/pharmacy/issue/' + consultationId);
  }

  public getAllConsultationsForCashier() {
    return this.httpClient.get<Consultation[]>('http://api.ayur.ngrok.io/api/cashier/consultations');
  }

  public chargeAndClose(consultationId) {
    return this.httpClient.get<boolean>('http://api.ayur.ngrok.io/api/cashier/charge/' + consultationId);
  }


  public getAllAppointments() {
    return this.httpClient.get<Appointment[]>('http://localhost:9090/api/appointments/all');
  }

  public searchAppointment2(doctorID, FromDate, ToDate) {
    return this.httpClient.get<Appointment[]>('http://localhost:9090/api/appointments/search/' + doctorID + '/' + FromDate + '/' + ToDate);
  }

  public searchTreatments(treatmentType, FromDate, ToDate) {
    return this.httpClient.get<Treatments[]>('http://localhost:9090/api/treatments/search/' + treatmentType + '/' + FromDate + '/' + ToDate);
  }


  public addNewTimeSlot(timeSlot) {
    return this.httpClient.post<TimeSlot[]>('http://api.ayur.ngrok.io/api/timeSlot/add', timeSlot, this.httpOptions);
  }

  public getAllTimeSlots() {
    return this.httpClient.get<TimeSlot[]>('http://api.ayur.ngrok.io/api/timeSlot/all');
  }

  public updateTimeSlot(timeSlot) {
    return this.httpClient.post<TimeSlot[]>('http://api.ayur.ngrok.io/api/timeSlot/update', timeSlot, this.httpOptions);
  }

  public deleteTimeSlot(timeSlotId) {
    return this.httpClient.get<TimeSlot[]>('http://api.ayur.ngrok.io/api/timeSlot/delete/' + timeSlotId);
  }


  public addNewAvailableTime(availableTime, doctorId) {
    return this.httpClient.post<AvailableTimeSlots>('http://api.ayur.ngrok.io/api/doctors/' + doctorId + '/availability', availableTime, this.httpOptions);
  }

  public getAllAvailableSlotsForDoc(doctorId) {
    return this.httpClient.get<AvailableTimeSlots[]>('http://api.ayur.ngrok.io/api/doctors/' + doctorId + '/availability/all');
  }

  public updateAvailableTimeSlot(availableTime, doctorId, slotId) {
    return this.httpClient.post<AvailableTimeSlots>('http://api.ayur.ngrok.io/api/doctors/' + doctorId
      + '/availability/update/' + slotId, availableTime, this.httpOptions);
  }

  public deleteAvailableTimeSlot(doctorId,slotId) {
    return this.httpClient.get<null>('http://api.ayur.ngrok.io/api/doctors/' + doctorId + '/availability/delete/'+slotId);
  }



  public getAllConsulations() {
    return this.httpClient.get<Consultation[]>('http://api.ayur.ngrok.io/api/consultation/all');
  }

  public searchConsultations(dateStart,dateTo) {
    return this.httpClient.get<Consultation[]>("http://api.ayur.ngrok.io/api/consultation/search/"+dateStart+"/"+dateTo);
  }

  public searchConsultationsWithTreatments(dateStart, dateTo, treatment) {
    return this.httpClient.get<Consultation[]>("http://api.ayur.ngrok.io/api/consultation/search/" + dateStart + "/" + dateTo + "/" + treatment);
  }
}

