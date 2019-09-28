package com.ayurveda.server.controller;

import com.ayurveda.server.domain.ContactUs;
import com.ayurveda.server.services.ContactUsService;
import com.ayurveda.server.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/contact-us")
@CrossOrigin("*")
public class ContactUsController {

    @Autowired
    private ContactUsService contactUsService;

    @PostMapping(path = "/add")
    public ResponseEntity<ContactUs> addNewMessage(@RequestBody ContactUs contactUs){
        ContactUs savedMessage = contactUsService.addNewMessage(contactUs);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping(path = "/find/{id}")
    public ResponseEntity<ContactUs> findMessage(@PathVariable String id){
        ContactUs savedMessage = contactUsService.getDataById(id);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping(path = "/findAll")
    public ResponseEntity<List<ContactUs>> findMessages(){
        List<ContactUs> allData = contactUsService.getAllData();
        return ResponseEntity.ok(allData);
    }









}
