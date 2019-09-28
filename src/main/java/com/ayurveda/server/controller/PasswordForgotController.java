package com.ayurveda.server.controller;

import com.ayurveda.server.domain.PasswordResetToken;
import com.ayurveda.server.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "/api/change")
@CrossOrigin("*")
public class PasswordForgotController {
    @Autowired
    private MemberService memberService;

    @PostMapping(path = "/password")
    public ResponseEntity addNewMessage(@RequestBody PasswordResetToken passwordResetToken, Principal principal) {
        passwordResetToken.setUserName(principal.getName());
        boolean isPasswordChanged = memberService.changePassword(passwordResetToken);
        if (isPasswordChanged) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

}
