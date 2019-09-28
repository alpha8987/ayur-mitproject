package com.ayurveda.server.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthenticatedUser extends User {


    public AuthenticatedUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public static AuthenticatedUser getAuthenticatedUserFromUser(UserAuthenticateData details) {
        List<SimpleGrantedAuthority> roles = new ArrayList<>(details.getUserRoleList().size());
        details.getUserRoleList().forEach(userRole -> roles.add(new SimpleGrantedAuthority(userRole.getRole())));
        return new AuthenticatedUser(
                details.getUserName(),
                details.getPassword(),
                roles);
    }

//    public List<String> getUserRoles() {
//        Collection<GrantedAuthority> authorities = super.getAuthorities();
//        List<String> roleList = new ArrayList<>();
//        authorities.stream().forEach(grantedAuthority -> roleList.add(grantedAuthority.getAuthority()));
//        return roleList;
//    }
}