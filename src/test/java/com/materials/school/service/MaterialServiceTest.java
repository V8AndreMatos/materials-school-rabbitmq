package com.materials.school.service;

import com.materials.school.dto.MaterialDTO;
import com.materials.school.entity.Material;
import com.materials.school.exception.ResourceNotFoundException;
import com.materials.school.messaging.MaterialProducer;
import com.materials.school.repository.MaterialRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MaterialServiceTest {

    @Mock
    private MaterialRepository materialRepository;

    @Mock
    private MaterialProducer materialProducer;

    @InjectMocks
    private MaterialService materialService;

    @Test
    //Teste para verificar se a matéria escolar foi salva e se a mensagem foi enviada para o RabbitMQ.
    void testCreateMaterial() {
        // Arrange
        MaterialDTO dto = new MaterialDTO();
        dto.setName("Musical Material");

        Material savedEntity = new Material(1L, "Musical Material");

        when(materialRepository.save(any(Material.class))).thenReturn(savedEntity);

        // Act
        MaterialDTO result = materialService.create(dto);

        // Assert
        assertNotNull(result);
        assertEquals("Musical Material ", result.getName());
        assertEquals(1L, result.getId());

        verify(materialRepository).save(any(Material.class));
        verify(materialProducer).sendMaterial(any(MaterialDTO.class));
    }

    @Test
    // Teste deve retornar o ID quando existe
    void testFindById_WhenMaterialExists() {
        // Arrange
        Long id = 1L;
        Material material = new Material(id, "Physics Material");
        when(materialRepository.findById(id)).thenReturn(Optional.of(material));

        // Act
        MaterialDTO result = materialService.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Physics Material", result.getName());

        verify(materialRepository).findById(id);
        System.out.println("Material encontrado : " + result.getName());

    }

    @Test
    void testFindById_WhenMaterialDoesNotExist() {
        // Arrange
        Long id = 99L;
        when(materialRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> materialService.findById(id)
        );

        assertEquals("ID 99 Not Found ", exception.getMessage());
        verify(materialRepository).findById(id);
        System.out.println(exception.getMessage());

    }

    @Test
    //Atualiza o material com sucesso
    void testUpdateMaterial_WhenExists() {
        // Arrange
        Long id = 1L;
        Material existingMaterial = new Material(id, " Old Name");
        Material updatedMaterial = new Material(id, " New Name");

        MaterialDTO updateDTO = new MaterialDTO();
        updateDTO.setName("New Name");

        when(materialRepository.findById(id)).thenReturn(Optional.of(existingMaterial));
        when(materialRepository.save(any(Material.class))).thenReturn(updatedMaterial);

        // Act
        MaterialDTO result = materialService.update(id, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals("New Name ", result.getName());
        assertEquals(id, result.getId());

        verify(materialRepository).findById(id);
        verify(materialRepository).save(existingMaterial);
        System.out.println("Material atualizado: " + result.getName());
    }

    @Test
    // Lança exceção se a matéria não existir
    void testUpdateMaterial_WhenNotExists() {
        // Arrange
        Long id = 99L;
        MaterialDTO updateDTO = new MaterialDTO();
        updateDTO.setName("New Name");

        when(materialRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> materialService.update(id, updateDTO)
        );

        assertEquals("Material with ID99 Not Found ", exception.getMessage());
        verify(materialRepository).findById(id);
        System.out.println(exception.getMessage());
    }

    @Test
    // Deleta o ID com sucesso
    void testDeleteMaterial_WhenExists() {
        // Arrange
        Long id = 1L;
        when(materialRepository.existsById(id)).thenReturn(true);
        doNothing().when(materialRepository).deleteById(id);

        // Act
        materialService.deleteById(id);

        // Assert
        verify(materialRepository).existsById(id);
        verify(materialRepository).deleteById(id);

    }

    @Test
    // Lança exceção se o ID não existir
    void testDeleteMaterial_WhenNotExists() {
        // Arrange
        Long id = 99L;
        when(materialRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> materialService.deleteById(id)
        );

        assertEquals("Material with ID 99 not found ", exception.getMessage());
        verify(materialRepository).existsById(id);
        verify(materialRepository, never()).deleteById(anyLong());
        System.out.println(exception.getMessage());
    }

}





