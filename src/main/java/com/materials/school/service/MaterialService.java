package com.materials.school.service;

import com.materials.school.dto.MaterialDTO;
import com.materials.school.entity.Material;
import com.materials.school.exception.ResourceNotFoundException;
import com.materials.school.repository.MaterialRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;

    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
   }

    // Find All Materials
    public List<MaterialDTO> findAll(){
        return materialRepository.findAll()
                .stream()
                .map(MaterialDTO::new)
                .collect(Collectors.toList());
    }

    // Find Material by ID
    public MaterialDTO findById(Long id) {
        Material material = materialRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID " +id+ " Not Found"));
        return new MaterialDTO(material);
    }

    // Create a new Material
    public MaterialDTO create(MaterialDTO materialDTO){
        Material entity = materialDTO.toEntity();
        entity = materialRepository.save(entity);
        return new MaterialDTO(entity);
    }

    // Update a Material
    public MaterialDTO update(Long id , MaterialDTO materialDTO) {

        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material with ID" +id+ " Not Found"));
        material.setName(materialDTO.getName());
        material = materialRepository.save(material);
        return new MaterialDTO(material);
    }

    public void deleteById(Long id){

        if (!materialRepository.existsById(id)) {
            throw new ResourceNotFoundException("Material with ID " + id + " not found");
        }
        materialRepository.deleteById(id);

    }

}
