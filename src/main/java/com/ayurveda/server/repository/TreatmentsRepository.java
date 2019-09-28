package com.ayurveda.server.repository;

import com.ayurveda.server.domain.Treatments;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TreatmentsRepository extends MongoRepository<Treatments,String> {

    @Query("{$and:[{type:{$regex:?0, $options:'i'}}, {$text: {$search: ?1}} ]}")
    List<Treatments> searchTreatment(String type,String keyword);

    @Query("{$and:[{type:{$regex:?0, $options:'i'}}]}")
    List<Treatments> searchTreatment(String type);

}

