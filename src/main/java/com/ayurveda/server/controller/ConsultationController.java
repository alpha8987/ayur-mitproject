package com.ayurveda.server.controller;


import com.ayurveda.server.domain.Consultation;
import com.ayurveda.server.services.ConsultationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/consultation")
@CrossOrigin("*")
public class ConsultationController {


    private ConsultationService consultationService;

    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @GetMapping(value = "/all")
    public List<Consultation> getAllConsultations() {
        return consultationService.findAllConsultations();
    }

    @GetMapping(path = "/search/{startDate}/{endDate}")
    public List<Consultation> search(@PathVariable String startDate, @PathVariable String endDate) {
        return consultationService.searchConsultations(startDate, endDate);
    }

    @GetMapping(path = "/search/{startDate}/{endDate}/{treatmentType}")
    public List<Consultation> search(@PathVariable String startDate, @PathVariable String endDate, @PathVariable String treatmentType) {
        return consultationService.searchConsultations(startDate, endDate, treatmentType);
    }
}
