package com.ayurveda.server.services;

import com.ayurveda.server.domain.Consultation;
import com.ayurveda.server.repository.ConsultationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ConsultationService {

    private ConsultationRepository consultationRepository;

    public ConsultationService(ConsultationRepository consultationRepository) {
        this.consultationRepository = consultationRepository;
    }

    public List<Consultation> findAllConsultations() {
        return consultationRepository.findAll().stream()
                .filter(consultation -> Objects.nonNull(consultation.getAppointment())).collect(Collectors.toList());
    }

    public List<Consultation> searchConsultations(String startDate, String endDate) {
        return consultationRepository.findAll().stream()
                .filter(consultation -> "-1".equalsIgnoreCase(startDate) || Objects.isNull(consultation.getAppointmentDate()) || !LocalDate.parse(startDate).isAfter(consultation.getAppointmentDate()))
                .filter(consultation -> "-1".equalsIgnoreCase(endDate) || Objects.isNull(consultation.getAppointmentDate()) || !LocalDate.parse(endDate).isAfter(consultation.getAppointmentDate()))
                .filter(consultation -> Objects.nonNull(consultation.getAppointment())).collect(Collectors.toList());
    }

    public List<Consultation> searchConsultations(String startDate, String endDate, String treatmentType) {
        return searchConsultations(startDate, endDate).stream()
                .filter(consultation -> "-1".equalsIgnoreCase(treatmentType) || consultation.getTreatment() != null && treatmentType.equalsIgnoreCase(consultation.getTreatment().getType()))
                .collect(Collectors.toList());
    }
}
