package com.ayurveda.server.controller;

import com.ayurveda.server.api.request.AvailableTimeRequest;
import com.ayurveda.server.api.response.DoctorResponse;
import com.ayurveda.server.domain.AvailableTimeSlots;
import com.ayurveda.server.dto.DoctorSessionInfo;
import com.ayurveda.server.exceptions.BadRequestException;
import com.ayurveda.server.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController// to indicate its a controller
@RequestMapping(path = "/api/doctors")
@CrossOrigin("*")//can access from any domian
public class DoctorController {

    private final DoctorService doctorService;//service ref

    @Autowired //auto connect
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }


    @GetMapping(path = "/nic/{nic}")
    public ResponseEntity<DoctorResponse> getDoctorByNIC(@PathVariable String nic) {
        String doctorNic = Optional.ofNullable(nic).orElseThrow(BadRequestException::new);
        return ResponseEntity.ok(doctorService.getDoctorByNic(doctorNic));//200 response ok
    }


    @GetMapping(path = "/sessions/{doctorId}")
    public List<DoctorSessionInfo> getFutureSessionInfo(@PathVariable String doctorId) {
        return doctorService.getFutureSessions(doctorId);
    }

    @GetMapping(path = "/session/start/{sessionId}")
    public AvailableTimeSlots startSession(@PathVariable String sessionId) {
        return doctorService.startSession(sessionId);
    }

    @GetMapping(path = "/session/end/{sessionId}")
    public void endSession(@PathVariable String sessionId) {
        doctorService.endSession(sessionId);
    }

    @PostMapping(path = "{doctorId}/availability")
    public ResponseEntity<AvailableTimeSlots> setAvailableTime(@PathVariable String doctorId,
                                                               @RequestBody AvailableTimeRequest request) {
        AvailableTimeSlots response = doctorService.setAvailableTime(doctorId, request);
        return ResponseEntity.ok(response);

    }

    @GetMapping(path = "/{doctorId}/availability/all")
    public List<AvailableTimeSlots> getAllAvailableSlots(@PathVariable String doctorId) {
        return doctorService.getAllAvailableSlots(doctorId);
    }

    @PostMapping(path = "{doctorId}/availability/update/{slotId}")
    public AvailableTimeSlots updateAvailability(@PathVariable String doctorId,
                                                 @RequestBody AvailableTimeRequest request, @PathVariable String slotId) {
        return doctorService.updateTimeSlot(doctorId, slotId, request);

    }

    @GetMapping(path = "/{doctorId}/availability/delete/{slotId}")
    public void deleteAvailableTimeSlot(@PathVariable String doctorId, @PathVariable String slotId) {
        doctorService.deleteAvailableTimeSlot(slotId);
    }


}
