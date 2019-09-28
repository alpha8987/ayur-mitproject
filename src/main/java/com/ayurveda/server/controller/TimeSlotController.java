package com.ayurveda.server.controller;

import com.ayurveda.server.domain.TimeSlot;
import com.ayurveda.server.services.TimeSlotService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/timeSlot")
@CrossOrigin("*")
public class TimeSlotController {

    private TimeSlotService timeSlotService;

    public TimeSlotController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    @PostMapping(path = "/add")
    public List<TimeSlot> addNewTimeSlot(@RequestBody TimeSlot timeSlot){
        return timeSlotService.addNewTimeSlot(timeSlot);
    }

    @GetMapping(path = "/all")
    public List<TimeSlot> getAllTimeSlots(){
        return timeSlotService.getAllTimeSlots();
    }

    @GetMapping(path = "/delete/{timeSlotId}")
    public List<TimeSlot> deleteTimeSlot(@PathVariable String timeSlotId){
        return timeSlotService.deleteTimeSlot(timeSlotId);
    }

    @PostMapping(path = "/update")
    public List<TimeSlot> updateTimeSlot(@RequestBody TimeSlot timeSlot){
        return timeSlotService.updateTimeSlot(timeSlot);
    }

}
