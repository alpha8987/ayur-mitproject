package com.ayurveda.server.services;

import com.ayurveda.server.domain.ContactUs;
import com.ayurveda.server.repository.ContactUsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactUsService {
    private ContactUsRepository contactUsRepository;


    @Autowired
    public ContactUsService(ContactUsRepository contactUsRepository) {
        this.contactUsRepository = contactUsRepository;
    }

    public ContactUs addNewMessage(ContactUs contactUs){
        return contactUsRepository.save(contactUs);
    }

    public ContactUs getDataById(String id){
        return contactUsRepository.findById(id).get();
    }

    public List<ContactUs> getAllData(){
        return contactUsRepository.findAll();
    }
}
