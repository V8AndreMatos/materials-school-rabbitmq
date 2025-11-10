package com.materials.school.service;

import com.materials.school.dto.MaterialDTO;
import com.materials.school.entity.Material;
import com.materials.school.exception.ResourceNotFoundException;
import com.materials.school.messaging.MaterialProducer;
import com.materials.school.repository.MaterialRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final MaterialProducer materialProducer;

    public MaterialService(MaterialRepository materialRepository, MaterialProducer materialProducer) {
        this.materialRepository = materialRepository;
        this.materialProducer = materialProducer;
    }

    // Find All Materials
    public List<MaterialDTO> findAll(){
        return materialRepository.findAll()
                .stream()
                .map(MaterialDTO::new)
                .collect(Collectors.toList());
    }

    // Find Material By ID
    public MaterialDTO findById(Long id) {
        Material material = materialRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID " +id+ " Not Found"));
        return new MaterialDTO(material);
    }

    // Create a new Material
    public MaterialDTO create(MaterialDTO materialDTO){
        Material entity = materialDTO.toEntity();
        entity = materialRepository.save(entity);

        // Envia a mensagem para a fila após salvar
        materialProducer.sendMaterial(new MaterialDTO(entity));

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

    // Delete Material By ID
    public void deleteById(Long id){

        if (!materialRepository.existsById(id)) {
            throw new ResourceNotFoundException("Material with ID " + id + " not found");
        }
        materialRepository.deleteById(id);

    }

    public List<Material> findAllRaw() {
        return materialRepository.findAll();
    }


}
