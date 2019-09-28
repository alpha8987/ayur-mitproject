package com.ayurveda.server.configuration;

import com.ayurveda.server.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().
                authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers( "/member/register").permitAll()
                .antMatchers( "/doctorAndSpecializationData","/executeDoctorSearch","/executeChannel").permitAll()
                .antMatchers( "/api/doctors/nic/**").permitAll()
                .antMatchers( "/api/treatments/**").permitAll()
                .antMatchers( "/api/contact-us/**").permitAll()
                .antMatchers( "/payment/**").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

//        auth.inMemoryAuthentication().withUser("user").password("{noop}123").roles("PATIENT","ADMIN");
        try {
            auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder());
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
