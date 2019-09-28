package com.ayurveda.server.controller;


import com.ayurveda.server.domain.Consultation;
import com.ayurveda.server.services.PharmacyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/pharmacy")
@CrossOrigin("*")
public class PharmacyController {
    private PharmacyService pharmacyService;

    public PharmacyController(PharmacyService pharmacyService) {
        this.pharmacyService = pharmacyService;
    }


    @GetMapping(path = "/consultations")
    public List<Consultation> getNextAppointment(){
        return pharmacyService.getCurrentWaitingConsultations();
    }

    @GetMapping(path = "/issue/{consultationId}")
    public boolean issueMedicine(@PathVariable String consultationId){
        return pharmacyService.issueMedicine(consultationId);

    }
}
