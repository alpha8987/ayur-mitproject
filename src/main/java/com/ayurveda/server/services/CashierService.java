package com.ayurveda.server.services;

import com.ayurveda.server.domain.Appointment;
import com.ayurveda.server.domain.AppointmentStatus;
import com.ayurveda.server.domain.Consultation;
import com.ayurveda.server.repository.AppointmentRepository;
import com.ayurveda.server.repository.ConsultationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CashierService {
    private ConsultationRepository consultationRepository;
    private AppointmentRepository appointmentRepository;

    public CashierService(ConsultationRepository consultationRepository, AppointmentRepository appointmentRepository) {
        this.consultationRepository = consultationRepository;
        this.appointmentRepository = appointmentRepository;
    }


    public List<Consultation> getCurrentWaitingConsultations() {
        List<Consultation> consultations = consultationRepository.findAllByAppointmentDateIs(LocalDate.now());
        return consultations.stream()
                .filter(consultation -> consultation.getAppointment().getStatus() == AppointmentStatus.POST_COLLECT)
                .collect(Collectors.toList());
    }

    public boolean chargeUserAndCloseConsultation(String consultationId) {
        Consultation consultation = consultationRepository.findConsultationById(consultationId);
        Appointment appointment = consultation.getAppointment();
        appointment.setStatus(AppointmentStatus.COMPLETED);
        Appointment save = appointmentRepository.save(appointment);
        consultation.setAppointment(save);
        consultationRepository.save(consultation);
        return true;
    }
}
