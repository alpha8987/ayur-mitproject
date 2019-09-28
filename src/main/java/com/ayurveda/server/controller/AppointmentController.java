package com.ayurveda.server.controller;

import com.ayurveda.server.api.response.AppointmentResponse;
import com.ayurveda.server.domain.Appointment;
import com.ayurveda.server.domain.Consultation;
import com.ayurveda.server.services.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/appointments")
@CrossOrigin("*")
public class AppointmentController {

    private AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping(path = "/find/{refId}/{nic}/{name}")
    public ResponseEntity<AppointmentResponse> findMessage(@PathVariable String refId, @PathVariable String nic, @PathVariable String name) {
        AppointmentResponse searchAppointment = appointmentService.searchAppointment(refId, nic, name);
        if (searchAppointment != null) {
            return ResponseEntity.ok(searchAppointment);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(path = "/mark-waiting/{appointmentId}")
    public ResponseEntity<Boolean> confirmPatientVisit(@PathVariable String appointmentId){
        boolean result = appointmentService.markAsWaitingConsultation(appointmentId);
        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/next/{sessionId}")
    public Consultation getNextAppointment(@PathVariable String sessionId){
        return appointmentService.getNextAppointmentForSession(sessionId);
    }

    @PostMapping(path = "/post-consult")
    public Consultation moveToPostConsult(@RequestBody Consultation consultation){
        return appointmentService.moveToPostConsult(consultation);
    }

    @GetMapping(path = "/all")
    public List<Appointment> getAllAppointments(){
        return appointmentService.getAllAppointments();
    }

    @GetMapping(path = "/search/{id}/{from}/{to}")
    public List<Appointment> searchAppointments(@PathVariable String id, @PathVariable String from, @PathVariable String to){
        return appointmentService.executeAppointmentSearch(id, from, to);
    }
}
