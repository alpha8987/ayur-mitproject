/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 2.14.505 on 2019-07-09 00:12:17.

export interface DoctorResponse extends ApiUserDetails {
  medicalLicense: string;
  specializationList: string[];
  userType: UserType;
  availableTimeSlotsList: AvailableTimeSlots[];
}

export interface DoctorSearchRequest {
  doctorId: string;
  specialization: string;
  channelDate: string;
}

export interface AvailableTimeRequest {
  roomNumber: number;
  date: Date;
  timeSlotId: string;
  maxPatientCount: number;
}

export interface AppointmentData {
  needDoctorNotification: boolean;
  title: string;
  firstName: string;
  lastName: string;
  nic: string;
  area: string;
  mobile: string;
  email: string;
  acceptedTerms: boolean;
  availableTimeSlot: AvailableTimeSlots;
  paymentMode: string;
  appointmentDoctorId: string;
  preferredNotificationMethod: string;
  local: boolean;
  member: boolean;
}

export interface AppointmentResponse {
  appointmentId: string;
  doctor: Doctor;
  patient: Patient;
  appointmentDate: Date;
  appointmentTimeSlot: TimeSlot;
  appointmentNumber: number;
  roomNumber: number;
  status: AppointmentStatus;
  roomCharge: number;
  doctorCharge: number;
  gatewayPaymentId: string;
  totalPayment: number;
  doctorNotificationSubscribed: boolean;
  appointmentRefNo: string;
  paymentMadeOn: Date;
  gatewayUrl: string;
  merchantId: string;
  returnUrl: string;
  cancelUrl: string;
  notifyUrl: string;
}

export interface DoctorAndSpecializationData {
  allDoctors: DoctorResponse[];
  specializationSet: string[];
}

export interface Member {
  userId: string;
  memberType: UserType;
  nic: string;
  firstName: string;
  lastName: string;
  title: string;
  email: string;
  dob: Date;
  address: string;
  country: string;
  mobileNo: string;
  emergencyContactName: string;
  emergencyContactNumber: string;
  notificationMethod: string;
  password: string;
  medicalLicense: string;
  specializationList: string[];
  consultationCharge: number;
}

export interface DoctorSessionInfo {
  sessionDate: Date;
  timeSlot: TimeSlot;
  totalPatients: number;
  paidPatients: number;
  waitingPatients: number;
  timeSlotId: string;
  sessionCanBeStarted: boolean;
  sessionStarted: boolean;
}

export interface ContactUs {
  contactUsId: string;
  name: string;
  email: string;
  message: string;
}

export interface Treatments {
  treatmentId: string;
  type: string;
  description: string;
  image: string;
}

export interface AvailableTimeSlots {
  id: string;
  roomNumber: number;
  roomCharge: number;
  date: Date;
  doctor: Doctor;
  timeSlot: TimeSlot;
  currentPatientCount: number;
  maxPatientCount: number;
  currentNumber: number;
  sessionStarted: boolean;
  sessionEnded: boolean;
}

export interface Consultation {
  id: string;
  patientId: string;
  appointment: Appointment;
  examinationFindings: string;
  currentCondition: string;
  nextAppointmentDate: Date;
  appointmentDate: Date;
  internalPrescriptionList: Prescription[];
  externalPrescriptionList: Prescription[];
  totalCharge: number;
  treatment: Treatments;
  previousConsultationList: Consultation[];
  currentSession: AvailableTimeSlots;
}

export interface Drug {
  id: string;
  name: string;
  description: string;
  manufactureDate: Date;
  expiryDate: Date;
  availableQuantity: number;
  reorderLevel: number;
  unitPrice: number;
}

export interface User {
  userName: string;
  status: string;
  userType: UserType;
  adminUser: boolean;
}

export interface PasswordResetToken {
  userName: string;
  oldPassword: string;
  newPassword: string;
}

export interface ApiUserDetails {
  id: string;
  nic: string;
  firstNames: string;
  lastName: string;
  dob: Date;
  address: string;
  email: string;
  joinedDate: Date;
  primaryContactNumber: string;
  emergencyContactPersonName: string;
  emergencyContactNumber: string;
  password: string;
  accountStatus: AccountStatus;
}

export interface Doctor {
  doctorId: string;
  doctorNic: string;
  medicalLicense: string;
  firstNames: string;
  lastName: string;
  dob: Date;
  address: string;
  email: string;
  joinedDate: Date;
  specialization: string[];
  primaryContactNumber: string;
  emergencyContactPersonName: string;
  emergencyContactPersonNumber: string;
  authenticateData: UserAuthenticateData;
  consultationCharge: number;
}

export interface Patient {
  patientId: string;
  patientNic: string;
  firstNames: string;
  lastName: string;
  title: string;
  dob: Date;
  address: string;
  country: string;
  email: string;
  joinedDate: Date;
  primaryContactNumber: string;
  emergencyContactPersonName: string;
  emergencyContactPersonNumber: string;
  authenticateData: UserAuthenticateData;
  notificationMethod: string;
}

export interface TimeSlot {
  id: string;
  startTime: Date;
  endTime: Date;
}

export interface Appointment {
  id: string;
  doctor: Doctor;
  patient: Patient;
  scheduledDate: Date;
  appointmentDate: Date;
  appointmentTimeSlot: TimeSlot;
  appointmentNumber: number;
  roomNumber: number;
  roomCharge: number;
  doctorCharge: number;
  gatewayPaymentId: string;
  paymentMadeOn: Date;
  doctorNotification: boolean;
  status: AppointmentStatus;
  appointmentRefNo: string;
}

export interface Prescription {
  drug: Drug;
  direction: string;
  qty: number;
}

export interface UserAuthenticateData {
  id: string;
  userName: string;
  password: string;
  userType: UserType;
  userRoleList: UserType[];
  accountStatus: AccountStatus;
}

export type AccountStatus = "REGISTERED" | "ACTIVATED" | "DELETED" | "LOCKED";

export type UserType = "PATIENT" | "DOCTOR" | "PHARMACIST" | "CASHIER" | "ADMIN";

export type AppointmentStatus = "INITIAL" | "PAYMENT_FAILED" | "CONFIRMED" | "CANCELED_BY_DOCTOR" | "WAITING_CONSULTATION" | "POST_CONSULTATION" | "POST_COLLECT" | "COMPLETED";
