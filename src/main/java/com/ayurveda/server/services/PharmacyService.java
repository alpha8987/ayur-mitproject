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
public class PharmacyService {
    private ConsultationRepository consultationRepository;
    private AppointmentRepository appointmentRepository;
    private DrugService drugService;

    public PharmacyService(ConsultationRepository consultationRepository, AppointmentRepository appointmentRepository, DrugService drugService) {
        this.consultationRepository = consultationRepository;
        this.appointmentRepository = appointmentRepository;
        this.drugService = drugService;
    }


    public List<Consultation> getCurrentWaitingConsultations() {
        List<Consultation> consultations = consultationRepository.findAllByAppointmentDateIs(LocalDate.now());
        return consultations.stream()
                .filter(consultation -> consultation.getAppointment().getStatus() == AppointmentStatus.POST_CONSULTATION)
                .collect(Collectors.toList());
    }

    public boolean issueMedicine(String consultationId) {
        Consultation consultation = consultationRepository.findConsultationById(consultationId);

        consultation.getInternalPrescriptionList()
                .forEach(prescription -> {
                    // update the drug repository
                    drugService.issueDrugFromStock(prescription.getDrug().getId(), prescription.getQty());

                    // calculate total charge
                    double totalCharge = consultation.getTotalCharge();
                    totalCharge += prescription.getDrug().getUnitPrice() * prescription.getQty();
                    consultation.setTotalCharge(totalCharge);
                });

        Appointment appointment = consultation.getAppointment();
        appointment.setStatus(AppointmentStatus.POST_COLLECT);
        Appointment save = appointmentRepository.save(appointment);
        consultation.setAppointment(save);
        consultationRepository.save(consultation);
        return true;
    }
}
