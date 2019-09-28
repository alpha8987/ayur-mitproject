package com.ayurveda.server.repository;

import com.ayurveda.server.domain.Appointment;
import com.ayurveda.server.domain.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {

    @Override
    <S extends Appointment> S insert(S entity);

    List<Appointment> findAppointmentsByAppointmentDateAndDoctor_DoctorIdAndAppointmentTimeSlot_Id(
            LocalDate appointmentDate, String doctor_doctorId, String appointmentTimeSlot_id);

    List<Appointment> findAllByPatient_PatientId(String patient_patientId);

    Appointment findAppointmentByAppointmentRefNoIs(String appointmentRefNo);

    Appointment findAppointmentByPatientIs(Patient patient);

    List<Appointment> findAppointmentsByAppointmentDateAndAppointmentTimeSlot_Id(LocalDate appointmentDate, String appointmentTimeSlot_id);
}
