package com.ayurveda.server.services;

import com.ayurveda.server.domain.Drug;
import com.ayurveda.server.repository.DrugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DrugService {

    private DrugRepository drugRepository;

    @Autowired
    public DrugService(DrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

    public Drug insertDrugToSystem(Drug drug) {
        return drugRepository.insert(drug);
    }

    public Drug retrieveDrugById(String drugId) {
        return drugRepository.findDrugByIdIs(drugId);
    }

    public Drug updateDrugToSystem(Drug drug) {
        Drug oldDrug = drugRepository.findDrugByIdIs(drug.getId());
        oldDrug.setExpiryDate(drug.getExpiryDate());
        oldDrug.setManufactureDate(drug.getManufactureDate());
        oldDrug.setReorderLevel(drug.getReorderLevel());
        oldDrug.setDescription(drug.getDescription());
        oldDrug.setAvailableQuantity(drug.getAvailableQuantity());
        oldDrug.setUnitPrice(drug.getUnitPrice());
        return drugRepository.save(oldDrug);
    }

    public boolean deleteDrug(String drugId) {
        return drugRepository.deleteDrugByIdIs(drugId) == 1L;
    }

    public List<Drug> getAllDrugs() {
        return drugRepository.findAll();
    }

    public void issueDrugFromStock(String drugId, int qty){
        Drug drug = drugRepository.findDrugByIdIs(drugId);
        drug.setAvailableQuantity(drug.getAvailableQuantity() - qty);
        drugRepository.save(drug);
    }

    public List<Drug> executeDrugSearch(String drugName, String expTo, String expFrom) {
        return drugRepository.findAll().stream()
                .filter(drug -> "-1".equalsIgnoreCase(drugName) || drugName.equalsIgnoreCase(drug.getName()))
                .filter(drug -> "-1".equalsIgnoreCase(expFrom) || !LocalDate.parse(expFrom).isAfter(drug.getExpiryDate()))
                .filter(drug -> "-1".equalsIgnoreCase(expTo) || !LocalDate.parse(expTo).isBefore(drug.getExpiryDate()))
                .collect(Collectors.toList());

    }
}
