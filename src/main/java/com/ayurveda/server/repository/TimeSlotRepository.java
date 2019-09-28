package com.ayurveda.server.repository;


import com.ayurveda.server.domain.TimeSlot;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface TimeSlotRepository extends MongoRepository<TimeSlot, String> {

    @Override
    <S extends TimeSlot> S insert(S entity);
}
