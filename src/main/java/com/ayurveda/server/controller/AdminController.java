package com.ayurveda.server.controller;

import com.ayurveda.server.api.request.TimeSlotRequest;
import com.ayurveda.server.api.response.TimeSlotResponse;
import com.ayurveda.server.exceptions.BadRequestException;
import com.ayurveda.server.exceptions.ServerErrorException;
import com.ayurveda.server.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(path = "/timeslot")
    public ResponseEntity<List<TimeSlotResponse>> getTimeSlots() {
        return ResponseEntity.ok(adminService.getAllTimeSlots());

    }


    @PostMapping(path = "/timeslot")
    public ResponseEntity<TimeSlotResponse> addTimeSlot(@RequestBody TimeSlotRequest request) {
        return ResponseEntity.ok(Optional
                .ofNullable(adminService.addTimeSlot(
                        Optional.ofNullable(request).orElseThrow(BadRequestException::new)))
                .orElseThrow(ServerErrorException::new));

    }

    @PutMapping(path = "/timeslot/{id}")
    public ResponseEntity<TimeSlotResponse> updateTimeSlot(@RequestBody TimeSlotRequest request, @PathVariable String id) {
        return ResponseEntity.ok(Optional
                .ofNullable(adminService.updateTimeSlot(
                        Optional.ofNullable(request).orElseThrow(BadRequestException::new), id))
                .orElseThrow(ServerErrorException::new));

    }
}
