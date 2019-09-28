package com.ayurveda.server.repository;

import com.ayurveda.server.domain.AvailableTimeSlots;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface AvailableTimeSlotsRepository extends MongoRepository<AvailableTimeSlots, String> {

    @Override
    <S extends AvailableTimeSlots> S save(S s);

    AvailableTimeSlots findAvailableTimeSlotsByDateAndDoctor_DoctorIdAndTimeSlot_Id(LocalDate date, String doctor_doctorId, String timeSlot_id);

    AvailableTimeSlots findAvailableTimeSlotsByDateEqualsAndDoctor_DoctorIdAndTimeSlot_Id(LocalDate date, String doctor_doctorId, String timeSlot_id);

    List<AvailableTimeSlots> findAvailableTimeSlotsByDateGreaterThanEqual(Date date);

    List<AvailableTimeSlots> findAvailableTimeSlotsByDateEquals(Date date);

    List<AvailableTimeSlots> findAvailableTimeSlotsByDoctor_DoctorId(String doctor_doctorId);

    @Query(value = "{_id:?0}")
    AvailableTimeSlots findAvailableTimeSlotsByIdIs(String id);


}
