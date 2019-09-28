package com.ayurveda.server.controller;

import com.ayurveda.server.domain.Drug;
import com.ayurveda.server.services.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/drug")
@CrossOrigin("*")
public class DrugController {

    private DrugService drugService;

    @Autowired
    public DrugController(DrugService drugService) {
        this.drugService = drugService;
    }

    @PostMapping(path = "/add")
    public Drug insertDrug(@RequestBody Drug drug){
        return drugService.insertDrugToSystem(drug);
    }

    @PostMapping(path = "/update")
    public Drug updateDrug(@RequestBody Drug drug){
        return drugService.updateDrugToSystem(drug);
    }

    @GetMapping(path = "/get/{drugId}")
    public Drug getDrugById(@PathVariable String drugId){
        return drugService.retrieveDrugById(drugId);
    }

    @GetMapping(path = "/delete/{drugId}")
    public boolean deleteDrug(@PathVariable String drugId){
        return drugService.deleteDrug(drugId);
    }

    @GetMapping(path = "/all")
    public List<Drug> getAllDrugs(){
        return drugService.getAllDrugs();
    }

    @GetMapping(path = "/search/{drugName}/{expFrom}/{expTo}")
    public List<Drug> search(@PathVariable String drugName, @PathVariable String expFrom, @PathVariable String expTo){
        return drugService.executeDrugSearch(drugName,expTo,expFrom);
    }
}
