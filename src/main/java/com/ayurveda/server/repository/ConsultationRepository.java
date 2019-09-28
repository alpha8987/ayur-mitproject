package com.ayurveda.server.repository;

import com.ayurveda.server.domain.Consultation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConsultationRepository extends MongoRepository<Consultation,String> {

    @Override
    <S extends Consultation> S save(S s);

    Consultation findConsultationById(String id);

    List<Consultation> findAllByPatientIdIs(String patientId);

    List<Consultation> findAllByAppointmentDateIs(LocalDate appointmentDate);

}

