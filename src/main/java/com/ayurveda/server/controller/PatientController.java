package com.ayurveda.server.controller;

import com.ayurveda.server.api.response.PatientResponse;
import com.ayurveda.server.domain.Appointment;
import com.ayurveda.server.exceptions.BadRequestException;
import com.ayurveda.server.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/patients")
public class PatientController {

    private final PatientService patientService;


    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /*---- Get request mappings ----*/

    @GetMapping()
    public ResponseEntity<List<PatientResponse>> getAllPatient() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping(path = "/nic/{nic}")
    public ResponseEntity<PatientResponse> getPatientByNIC(@PathVariable String nic) {
        String patientNic = Optional.ofNullable(nic).orElseThrow(BadRequestException::new);
        return ResponseEntity.ok(patientService.getPatientByNic(patientNic));
    }

    @GetMapping(path = "/{nic}/appointment")
    public ResponseEntity<List<Appointment>> getPatientAppointmentsByNic(@PathVariable String nic) {
        String patientNic = Optional.ofNullable(nic).orElseThrow(BadRequestException::new);
        return ResponseEntity.ok(patientService.getPatientAppointmentsByNic(patientNic));
    }


    @DeleteMapping(path = "/nic/{nic}")
    public ResponseEntity<PatientResponse> deletePatientByNic(@PathVariable String nic) {
        String patientNic = Optional.ofNullable(nic).orElseThrow(BadRequestException::new);
        return ResponseEntity.ok(patientService.deletePatientByNic(patientNic));
    }


}
