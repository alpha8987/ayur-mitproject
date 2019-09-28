package com.ayurveda.server.services;

import com.ayurveda.server.domain.Treatments;
import com.ayurveda.server.repository.TreatmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreatmentsService {
    private TreatmentsRepository treatmentsRepository;

    @Autowired
    public TreatmentsService(TreatmentsRepository treatmentsRepository) {
        this.treatmentsRepository = treatmentsRepository;
    }

    public Treatments addNewMessage(Treatments treatments) {
        return treatmentsRepository.save(treatments);
    }

    public Treatments getDataById(String id) {
        return treatmentsRepository.findById(id).get();
    }

    public List<Treatments> getAllData() {
        return treatmentsRepository.findAll();
    }

    public List<Treatments> search(String type, String keyword) {
        if (keyword == null || keyword.isEmpty())
            return treatmentsRepository.searchTreatment(type);
        return treatmentsRepository.searchTreatment(type, keyword);
    }
}

