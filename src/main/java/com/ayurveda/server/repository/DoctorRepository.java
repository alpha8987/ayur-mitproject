package com.ayurveda.server.repository;

import com.ayurveda.server.domain.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DoctorRepository extends MongoRepository<Doctor, String> {



    @Override
    <S extends Doctor> S insert(S entity);

    @Override
    List<Doctor> findAll();

    @Override
    <S extends Doctor> S save(S s);

    Doctor findDoctorByDoctorId(String doctorId);

    Doctor findDoctorByDoctorNic(String doctorNic);

    List<Doctor> findAllByLastNameLike(String lastName);

    Doctor findDoctorByMedicalLicense(String medicalLicense);

    List<Doctor> findAllByFirstNamesLike(String firstNames);

    List<Doctor> findAllByspecializationContaining(String specialization);

    void deleteDoctorByDoctorNicIs(String doctorNic);

    Doctor findDoctorByAuthenticateData_Id(String authenticateData_id);


}
