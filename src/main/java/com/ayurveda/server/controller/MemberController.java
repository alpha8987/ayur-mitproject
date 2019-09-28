package com.ayurveda.server.controller;

import com.ayurveda.server.dto.Member;
import com.ayurveda.server.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin("*")
@RequestMapping(path = "/member")
public class MemberController {

    private MemberService memberService;


    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Member> registerMember(@RequestBody Member member){
        Member registeredMember = memberService.registerMember(member);
        return ResponseEntity.ok(registeredMember);
    }
}
