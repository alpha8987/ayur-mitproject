package com.ayurveda.server.services;

import com.ayurveda.server.domain.AuthenticatedUser;
import com.ayurveda.server.domain.UserAuthenticateData;
import com.ayurveda.server.repository.UserAuthenticationDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserAuthenticationDataRepository repository;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        UserAuthenticateData user = repository.findUserAuthenticateDataByUserNameIs(userName);
        return AuthenticatedUser.getAuthenticatedUserFromUser(user);
    }
}
