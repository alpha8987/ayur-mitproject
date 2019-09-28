package com.ayurveda.server.utility;

import com.ayurveda.server.api.request.TimeSlotRequest;
import com.ayurveda.server.api.response.AppointmentResponse;
import com.ayurveda.server.api.response.DoctorResponse;
import com.ayurveda.server.api.response.PatientResponse;
import com.ayurveda.server.api.response.TimeSlotResponse;
import com.ayurveda.server.domain.Appointment;
import com.ayurveda.server.domain.Doctor;
import com.ayurveda.server.domain.Patient;
import com.ayurveda.server.domain.TimeSlot;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Utils {

    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

    public static PatientResponse convertDBPatientToApiResponse(Patient patient) {
        PatientResponse response = new PatientResponse();
        response.setId(patient.getPatientId());
        response.setAccountStatus(patient.getAuthenticateData().getAccountStatus());
        response.setPatientId(patient.getPatientId());
        response.setAddress(patient.getAddress());
        response.setDob(patient.getDob());
        response.setEmail(patient.getEmail());
        response.setPrimaryContactNumber(patient.getPrimaryContactNumber());
        response.setEmergencyContactPersonName(patient.getEmergencyContactPersonName());
        response.setEmergencyContactNumber(patient.getEmergencyContactPersonNumber());
        response.setNic(patient.getPatientNic());
        response.setFirstNames(patient.getFirstNames());
        response.setLastName(patient.getLastName());
        response.setJoinedDate(patient.getJoinedDate());
        return response;
    }

    public static DoctorResponse convertDBDoctorToApiResponse(Doctor doctor) {
        DoctorResponse response = new DoctorResponse();
        response.setId(doctor.getDoctorId());
        response.setAccountStatus(doctor.getAuthenticateData().getAccountStatus());
        response.setAddress(doctor.getAddress());
        response.setDob(doctor.getDob());
        response.setEmail(doctor.getEmail());
        response.setPrimaryContactNumber(doctor.getPrimaryContactNumber());
        response.setEmergencyContactPersonName(doctor.getEmergencyContactPersonName());
        response.setEmergencyContactNumber(doctor.getEmergencyContactPersonNumber());
        response.setNic(doctor.getDoctorNic());
        response.setFirstNames(doctor.getFirstNames());
        response.setLastName(doctor.getLastName());
        response.setJoinedDate(doctor.getJoinedDate());
        response.setMedicalLicense(doctor.getMedicalLicense());
        response.setSpecializationList(doctor.getSpecialization());

        return response;
    }

    public static Patient createTemporaryPatient(String nic, String firstName, String lastName, String mobile) {
        Patient patient = new Patient();
        patient.setPatientNic(nic);
        patient.setFirstNames(firstName);
        patient.setLastName(lastName);
        patient.setPrimaryContactNumber(mobile);

        return patient;
    }

    public static AppointmentResponse convertDBAppointmentToResponse(Appointment appointment) {
        AppointmentResponse response = new AppointmentResponse();
        response.setAppointmentDate(appointment.getAppointmentDate());
        response.setAppointmentId(appointment.getId());
        response.setAppointmentNumber(appointment.getAppointmentNumber());
        response.setAppointmentTimeSlot(appointment.getAppointmentTimeSlot());
        response.setDoctor(appointment.getDoctor());
        response.setPatient(appointment.getPatient());
        response.setRoomNumber(appointment.getRoomNumber());
        response.setStatus(appointment.getStatus());
        response.setRoomCharge(appointment.getRoomCharge());
        response.setDoctorCharge(appointment.getDoctorCharge());
        response.setTotalPayment((appointment.getRoomCharge() + appointment.getDoctorCharge()));
        response.setGatewayPaymentId(appointment.getGatewayPaymentId());
        response.setDoctorNotificationSubscribed(appointment.isDoctorNotification());
        response.setAppointmentRefNo(appointment.getAppointmentRefNo());
        response.setPaymentMadeOn(appointment.getPaymentMadeOn());
        return response;
    }

    public static TimeSlotResponse convertDBTimeSlotToResponse(TimeSlot timeSlot) {
        TimeSlotResponse response = new TimeSlotResponse();
        response.setId(timeSlot.getId());
        response.setStartTime(TIME_FORMAT.format(timeSlot.getStartTime()));
        response.setEndTime(TIME_FORMAT.format(timeSlot.getEndTime()));
        return response;
    }

    public static TimeSlot convertTimeSlotToDB(TimeSlotRequest timeSlot) throws ParseException {
        TimeSlot response = new TimeSlot();
        response.setStartTime(TIME_FORMAT.parse(timeSlot.getStartTime()));
        response.setEndTime(TIME_FORMAT.parse(timeSlot.getEndTime()));
        return response;
    }
}
