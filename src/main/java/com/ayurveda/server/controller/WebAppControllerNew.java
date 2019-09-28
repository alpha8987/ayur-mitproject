package com.ayurveda.server.controller;

import com.ayurveda.server.api.request.DoctorSearchRequest;
import com.ayurveda.server.api.response.AppointmentResponse;
import com.ayurveda.server.api.response.DoctorAndSpecializationData;
import com.ayurveda.server.api.response.DoctorResponse;
import com.ayurveda.server.dto.AppointmentData;
import com.ayurveda.server.exceptions.BadRequestException;
import com.ayurveda.server.services.AppointmentService;
import com.ayurveda.server.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(path = "/")
@CrossOrigin
public class WebAppControllerNew {


    private DoctorService doctorService;
    private AppointmentService appointmentService;

    @Autowired
    public WebAppControllerNew(DoctorService doctorService, AppointmentService appointmentService) {
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
    }

    @GetMapping(path = "doctorAndSpecializationData")
    public ResponseEntity<DoctorAndSpecializationData> homePageData() {
        List<DoctorResponse> allDoctors = doctorService.getAllDoctors();
        Set<String> specializationSet = doctorService.extractSpecializationFromDoctorList(allDoctors);
        return ResponseEntity.ok(new DoctorAndSpecializationData(allDoctors, specializationSet));
    }


    @PostMapping(path = "executeDoctorSearch")
    public ResponseEntity<DoctorAndSpecializationData> executeDoctorSearch(@RequestBody DoctorSearchRequest searchRequest) {
        try {
            List<DoctorResponse> allDoctors = doctorService.executeDoctorSearch(searchRequest.getDoctorId(),
                    searchRequest.getSpecialization(), searchRequest.getChannelDate());
            Set<String> specializationSet = doctorService.extractSpecializationFromDoctorList(allDoctors);
            return ResponseEntity.ok(new DoctorAndSpecializationData(allDoctors, specializationSet));
        } catch (Exception e) {
            throw BadRequestException.getInstance();
        }
    }

    @PostMapping(path = "executeChannel")
    public ResponseEntity<AppointmentResponse> executeChannel(@RequestBody AppointmentData appointmentData) {
        AppointmentResponse response = appointmentService.executeChannel(appointmentData);

        return ResponseEntity.ok(response);
    }


}
