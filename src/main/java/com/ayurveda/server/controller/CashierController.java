package com.ayurveda.server.controller;

import com.ayurveda.server.domain.Consultation;
import com.ayurveda.server.services.CashierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/cashier")
@CrossOrigin("*")
public class CashierController {

    private CashierService cashierService;

    public CashierController(CashierService cashierService) {
        this.cashierService = cashierService;
    }

    @GetMapping(path = "/consultations")
    public List<Consultation> getNextAppointment() {
        return cashierService.getCurrentWaitingConsultations();
    }

    @GetMapping(path = "/charge/{consultationId}")
    public boolean issueMedicine(@PathVariable String consultationId){
        return cashierService.chargeUserAndCloseConsultation(consultationId);

    }
}
