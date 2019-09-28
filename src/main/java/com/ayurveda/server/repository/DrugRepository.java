package com.ayurveda.server.repository;

import com.ayurveda.server.domain.Drug;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DrugRepository extends MongoRepository<Drug,String> {

    Drug findDrugByIdIs(String id);

    Long deleteDrugByIdIs(String id);
}
