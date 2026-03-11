package com.jaugustosf.setup_manager.service;

import com.jaugustosf.setup_manager.exception.RecordNotFoundException;
import com.jaugustosf.setup_manager.exception.BusinessRuleException;
import com.jaugustosf.setup_manager.model.Equipment;
import com.jaugustosf.setup_manager.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository repository;

    public Equipment save(Equipment equipment) {
        Optional<Equipment> existingEquipment = repository.findByName(equipment.getName());

        if (existingEquipment.isPresent()) {
            throw new BusinessRuleException("An equipment with the name " + equipment.getName() + " already exists.");
        }

        return repository.save(equipment);
    }

    public List<Equipment> saveAll(List<Equipment> equipments) {
        for (Equipment eq : equipments) {
            Optional<Equipment> existingEquipment = repository.findByName(eq.getName());

            if (existingEquipment.isPresent()) {
                throw new BusinessRuleException("An equipment with the name " + eq.getName() + " already exists.");
            }
        }

        return repository.saveAll(equipments);
    }

    public List<Equipment> listByCategory(String category){
        if (category != null){
            return repository.findByCategoryIgnoreCase(category);
        }
        return repository.findAll();
    }

    public List<Equipment> listAll() {
        return repository.findAll();
    }

    public Equipment findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RecordNotFoundException("Equipment with ID " + id + " does not exist in your setup."));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
