package com.ayurveda.server.repository;

import com.ayurveda.server.domain.UserAuthenticateData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

@Repository
public interface UserAuthenticationDataRepository extends MongoRepository<UserAuthenticateData,String> {

    @Override
    <S extends UserAuthenticateData> S save(S s);

    @Override
    <S extends UserAuthenticateData> S insert(S entity);

    @Override
    Optional<UserAuthenticateData> findById(String aLong);

    UserAuthenticateData findUserAuthenticateDataByUserNameIs(@NotEmpty String userName);


    UserAuthenticateData findDistinctByUserNameAndPassword(@NotEmpty String userName, @NotEmpty String password);
}
