package com.ayurveda.server.repository;

import com.ayurveda.server.domain.ContactUs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactUsRepository extends MongoRepository<ContactUs,String> {

}
