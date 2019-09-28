package com.ayurveda.server.repository;

import com.ayurveda.server.domain.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends MongoRepository<Patient, String> {
    @Override
    List<Patient> findAll();

    @Override
    <S extends Patient> S insert(S entity);

    Patient findPatientByPatientNicIs(String patientNic);

    void deletePatientByPatientNicIs(String doctorNic);

    List<Patient> findPatientByFirstNamesIsOrLastNameIs(String firstNames, String lastName);

    Patient findPatientByAuthenticateData_Id(String authenticateData_id);
}
