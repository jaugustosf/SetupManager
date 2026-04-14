package com.jaugustosf.setup_manager.controller;

import com.jaugustosf.setup_manager.model.Equipment;
import com.jaugustosf.setup_manager.service.EquipmentService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.jaugustosf.setup_manager.dto.EquipmentRequestDTO;
import com.jaugustosf.setup_manager.dto.EquipmentResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipmentResponseDTO insertEquipment(@Valid @RequestBody EquipmentRequestDTO requestDTO) {
        Equipment newEquipment = requestDTO.convertToEntity();
        Equipment savedEquipment = service.save(newEquipment);
        return new EquipmentResponseDTO(savedEquipment);
    }

    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public List<EquipmentResponseDTO> batchInsert(@Valid @RequestBody List<EquipmentRequestDTO> requestDTO) {
        List<Equipment> newEquipments = requestDTO.stream()
                .map(EquipmentRequestDTO::convertToEntity)
                .toList();
        List<Equipment> savedEquipments = service.saveAll(newEquipments);
        return savedEquipments.stream()
                .map(EquipmentResponseDTO::new)
                .toList();
    }

    @GetMapping("/{id}")
    public Equipment listEquipmentById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    public Page<EquipmentResponseDTO> listEquipment(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @ParameterObject @PageableDefault(size = 10, page = 0) Pageable pageable) { //Using ParameterObject because SpringDoc try doc all Pageable interface.
        Page<Equipment> list = service.listEquipment(name, category, pageable);
        return list.map(EquipmentResponseDTO::new);
    }

    @PutMapping("/{id}")
    public Equipment updateEquipment(@Valid @PathVariable Long id, @RequestBody Equipment updatedEquipment) {
        updatedEquipment.setId(id);
        return service.save(updatedEquipment);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEquipment(@PathVariable Long id) {
        service.deleteById(id);
    }
}
