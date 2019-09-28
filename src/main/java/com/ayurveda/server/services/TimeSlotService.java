package com.ayurveda.server.services;

import com.ayurveda.server.domain.TimeSlot;
import com.ayurveda.server.repository.TimeSlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimeSlotService {

    private TimeSlotRepository timeSlotRepository;

    public TimeSlotService(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    public List<TimeSlot> addNewTimeSlot(TimeSlot timeSlot) {
        timeSlotRepository.save(timeSlot);
        return timeSlotRepository.findAll();
    }

    public List<TimeSlot> getAllTimeSlots() {
        return timeSlotRepository.findAll();
    }

    public List<TimeSlot> deleteTimeSlot(String timeSlotId) {
        timeSlotRepository.deleteById(timeSlotId);
        return timeSlotRepository.findAll();
    }

    public List<TimeSlot> updateTimeSlot(TimeSlot timeSlot) {
        Optional<TimeSlot> dbOpt = timeSlotRepository.findById(timeSlot.getId());
        if (dbOpt.isPresent()){
            TimeSlot slot = dbOpt.get();
            slot.setStartTime(timeSlot.getStartTime());
            slot.setEndTime(timeSlot.getEndTime());
            timeSlotRepository.save(slot);
        }
        return timeSlotRepository.findAll();
    }
}
