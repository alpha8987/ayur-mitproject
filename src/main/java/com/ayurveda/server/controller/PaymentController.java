package com.ayurveda.server.controller;

import com.ayurveda.server.api.response.AppointmentResponse;
import com.ayurveda.server.dto.MobileNotifyPayment;
import com.ayurveda.server.services.DoctorService;
import com.ayurveda.server.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/payment")
@CrossOrigin
public class PaymentController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping(path = "summary/{appointmentId}")
    public ResponseEntity<AppointmentResponse> paymentReturn(Model model,
                                @PathVariable("appointmentId") String appointmentId) {
        AppointmentResponse response = doctorService.findSingleAppointment(appointmentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "cancel")
    public ModelAndView paymentCancel(Model model) {
        return new ModelAndView("redirect:http://localhost:4200");
    }

    @PostMapping(path = "notify")
    public ResponseEntity paymentNotify(Model model,
                                        @RequestBody MultiValueMap<String, String> formData) {
        System.out.println("in payment notification ");
        paymentService.updatePaymentStatus(formData);
        return ResponseEntity.ok(null);
    }
    @PostMapping(path = "notify/mobile")
    public ResponseEntity paymentNotifyMobile(Model model,
                                              @RequestBody MobileNotifyPayment notifyPayment) {
        System.out.println("in payment notification ");
        MultiValueMap<String,String> formData = new LinkedMultiValueMap();
        formData.add("merchant_id",notifyPayment.getMerchantId());
        formData.add("order_id", notifyPayment.getOrderId());
        formData.add("payment_id", notifyPayment.getPaymentId());
        formData.add("status_code", notifyPayment.getStatusCode());
        paymentService.updatePaymentStatus(formData);
        return ResponseEntity.ok(null);
    }

}
