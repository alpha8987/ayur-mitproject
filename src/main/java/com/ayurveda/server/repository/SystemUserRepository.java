package com.ayurveda.server.repository;

import com.ayurveda.server.domain.SystemUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemUserRepository extends MongoRepository<SystemUser, String> {

    SystemUser findSystemUserByAuthenticateData_Id(String authenticateData_id);
}
