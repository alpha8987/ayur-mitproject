package com.ayurveda.server.services;

import com.ayurveda.server.domain.Appointment;
import com.ayurveda.server.domain.AppointmentStatus;
import com.ayurveda.server.domain.Patient;
import com.ayurveda.server.exceptions.NoAvailableAppointmentException;
import com.ayurveda.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PaymentService {

    private DoctorRepository doctorRepository;
    private UserAuthenticationDataRepository authenticationDataRepository;
    private AvailableTimeSlotsRepository availableTimeSlotsRepository;
    private PatientRepository patientRepository;
    private AppointmentRepository appointmentRepository;
    private TimeSlotRepository timeSlotRepository;
    private MessagingService messagingService;

    @Autowired
    public PaymentService(DoctorRepository doctorRepository, UserAuthenticationDataRepository authenticationDataRepository,
                          AvailableTimeSlotsRepository availableTimeSlotsRepository,
                          PatientRepository patientRepository,
                          AppointmentRepository appointmentRepository,
                          TimeSlotRepository timeSlotRepository,
                          MessagingService messagingService) {
        this.doctorRepository = doctorRepository;
        this.authenticationDataRepository = authenticationDataRepository;
        this.availableTimeSlotsRepository = availableTimeSlotsRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.messagingService = messagingService;
    }


    public void updatePaymentStatus(MultiValueMap<String, String> formData) {
        String merchant_id = formData.getFirst("merchant_id");
        String order_id = formData.getFirst("order_id");
        String payment_id = formData.getFirst("payment_id");
        String status_code = formData.getFirst("status_code");

        Appointment appointment = appointmentRepository.findById(order_id).orElseThrow(NoAvailableAppointmentException::new);
        if ("2".equals(status_code)) {
            appointment.setStatus(AppointmentStatus.CONFIRMED);
            Patient patient = appointment.getPatient();
            String message = "Your Appointment for Dr." + appointment.getDoctor().getLastName()
                    + " on " + appointment.getAppointmentDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    + " is confirmed. The reference no is [" + appointment.getAppointmentRefNo() + "]";
            if ("sms".equalsIgnoreCase(patient.getNotificationMethod())) {
                messagingService.sendSms(message, patient.getPrimaryContactNumber());
            }else if ("email".equalsIgnoreCase(patient.getNotificationMethod())){
                messagingService.sendEmail(message,patient.getEmail());
            }
        } else if ("-1".equals(status_code) || "-2".equals(status_code)) {
            appointment.setStatus(AppointmentStatus.PAYMENT_FAILED);
        }
        appointment.setGatewayPaymentId(payment_id);
        appointment.setPaymentMadeOn(LocalDate.now());
        appointmentRepository.save(appointment);
    }
}
