package com.ayurveda.server.services;

import com.ayurveda.server.api.request.TimeSlotRequest;
import com.ayurveda.server.api.response.TimeSlotResponse;
import com.ayurveda.server.domain.TimeSlot;
import com.ayurveda.server.exceptions.ResourceNotExistsException;
import com.ayurveda.server.exceptions.ServerErrorException;
import com.ayurveda.server.repository.*;
import com.ayurveda.server.utility.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private final DoctorRepository doctorRepository;
    private final UserAuthenticationDataRepository authenticationDataRepository;
    private final AvailableTimeSlotsRepository availableTimeSlotsRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final TimeSlotRepository timeSlotRepository;

    @Autowired
    public AdminService(DoctorRepository doctorRepository,
                        UserAuthenticationDataRepository authenticationDataRepository,
                        AvailableTimeSlotsRepository availableTimeSlotsRepository,
                        PatientRepository patientRepository,
                        AppointmentRepository appointmentRepository, TimeSlotRepository timeSlotRepository) {
        this.doctorRepository = doctorRepository;
        this.authenticationDataRepository = authenticationDataRepository;
        this.availableTimeSlotsRepository = availableTimeSlotsRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    public TimeSlotResponse addTimeSlot(TimeSlotRequest request) {
        try {
            TimeSlot timeSlot = Utils.convertTimeSlotToDB(request);
            timeSlot = timeSlotRepository.insert(timeSlot);

            return Utils.convertDBTimeSlotToResponse(timeSlot);
        } catch (ParseException e) {
            throw ServerErrorException.getInstance();
        }
    }

    public TimeSlotResponse updateTimeSlot(TimeSlotRequest request, String id) {
        TimeSlot timeSlot = timeSlotRepository.findById(id).orElseThrow(ResourceNotExistsException::getInstance);
        try {
            timeSlot.setStartTime(Utils.TIME_FORMAT.parse(request.getStartTime()));
            timeSlot.setEndTime(Utils.TIME_FORMAT.parse(request.getEndTime()));
            timeSlot = timeSlotRepository.save(timeSlot);

            return Utils.convertDBTimeSlotToResponse(timeSlot);
        } catch (ParseException e) {
            throw ServerErrorException.getInstance();
        }
    }

    public List<TimeSlotResponse> getAllTimeSlots() {
        return timeSlotRepository.findAll().parallelStream()
                .map(Utils::convertDBTimeSlotToResponse)
                .collect(Collectors.toList());
    }
}
