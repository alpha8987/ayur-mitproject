package com.ayurveda.server.controller;

import com.ayurveda.server.domain.UserAuthenticateData;
import com.ayurveda.server.domain.UserType;
import com.ayurveda.server.dto.Member;
import com.ayurveda.server.dto.User;
import com.ayurveda.server.repository.UserAuthenticationDataRepository;
import com.ayurveda.server.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(path = "/user")
@CrossOrigin("*")
public class UserController {

    private UserAuthenticationDataRepository authenticationDataRepository;
    private MemberService memberService;

    @Autowired
    public UserController(UserAuthenticationDataRepository authenticationDataRepository, MemberService memberService) {
        this.authenticationDataRepository = authenticationDataRepository;
        this.memberService = memberService;
    }


    @GetMapping(path = "/validateLogin")
    public User validateLogin(Principal principal) {
        UserAuthenticateData authUser = authenticationDataRepository.findUserAuthenticateDataByUserNameIs(principal.getName());
        User user = new User();
        user.setUserName(authUser.getUserName());
        user.setStatus(String.valueOf(authUser.getAccountStatus()));
        user.setUserType(authUser.getUserType());
        user.setAdminUser(authUser.getUserRoleList().stream().anyMatch(userRoleType -> userRoleType== UserType.ADMIN));
        return user;
    }
    @GetMapping(path = "/get")
    public Member getCurrentUser(Principal principal) {
        UserAuthenticateData authUser = authenticationDataRepository.findUserAuthenticateDataByUserNameIs(principal.getName());
        return memberService.retrieveMemberData(authUser);
    }


}
