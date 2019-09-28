package com.ayurveda.server.controller;

import com.ayurveda.server.domain.Treatments;
import com.ayurveda.server.services.TreatmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/treatments")
@CrossOrigin("*")
public class TreatmentsController {

    @Autowired
    private TreatmentsService treatmentsService;

    @PostMapping(path = "/add")
    public ResponseEntity<Treatments> addNewTreatment(@RequestBody Treatments treatments) {
        Treatments savedMessage = treatmentsService.addNewMessage(treatments);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping(path = "/types")
    public ResponseEntity<List<String>> getAllTypes() {
        List<String> typeList = treatmentsService.getAllData()
                .stream().map(Treatments::getType).distinct().collect(Collectors.toList());
        return ResponseEntity.ok(typeList);
    }

    @GetMapping(path = "/search/{type}/{keyword}")
    public ResponseEntity<List<Treatments>> searchTreatment(@PathVariable String type, @PathVariable String keyword) {
        return ResponseEntity.ok(treatmentsService.search(type, keyword));
    }

    @GetMapping(path = "/search/{type}")
    public ResponseEntity<List<Treatments>> searchTreatment(@PathVariable String type) {
        return ResponseEntity.ok(treatmentsService.search(type, null));
    }

    @GetMapping(path = "/all")
    public List<Treatments> getAllTreatments(){
        return treatmentsService.getAllData();
    }


}
