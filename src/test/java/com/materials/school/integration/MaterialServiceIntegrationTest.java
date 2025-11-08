package com.materials.school.integration;

import com.materials.school.dto.MaterialDTO;
import com.materials.school.exception.ResourceNotFoundException;
import com.materials.school.repository.MaterialRepository;
import com.materials.school.service.MaterialService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // use application-test.yml se quiser
class MaterialServiceIntegrationTest {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private MaterialRepository materialRepository;

    @Test
    // Test to find existing school subjects
    void testCreateAndFindMaterial() {
        // Arrange
        MaterialDTO dto = new MaterialDTO();
        dto.setName("Integration Test Material");

        // Act
        MaterialDTO saved = materialService.create(dto);
        MaterialDTO found = materialService.findById(saved.getId());

        // Assert
        assertNotNull(found);
        assertEquals("Integration Test Material", found.getName());
    }

    @Test
    void testUpdateMaterialIntegration() {
        // Test to verify that a school material has been successfully updated with a valid ID
        MaterialDTO original = new MaterialDTO();
        original.setName("Original Name");

        MaterialDTO saved = materialService.create(original);

        MaterialDTO updateDTO = new MaterialDTO();
        updateDTO.setName("Updated Name");

        // Act
        MaterialDTO updated = materialService.update(saved.getId(), updateDTO);
        MaterialDTO found = materialService.findById(saved.getId());

        // Assert
        assertNotNull(updated);
        assertEquals("Updated Name", updated.getName());
        assertEquals(saved.getId(), updated.getId());

        assertEquals("Updated Name", found.getName());
    }

    @Test
        // Test to verify that a school material has been successfully updated with an invalid ID
    void testUpdateMaterial_WhenIdDoesNotExist() {
        // Arrange
        Long nonexistentId = 999L;
        MaterialDTO updateDTO = new MaterialDTO();
        updateDTO.setName("Should Not Update");

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> materialService.update(nonexistentId, updateDTO)
        );

        assertEquals("Material with ID999 Not Found", exception.getMessage());
        System.out.println("Exception message: " + exception.getMessage());
    }

    @Test
    void testDeleteMaterial_WhenIdExists() {
    // Test to verify that a school material has been successfully deleted with a valid ID

        // Arrange
        MaterialDTO dto = new MaterialDTO();
        dto.setName("Material to Delete");

        MaterialDTO saved = materialService.create(dto);
        Long id = saved.getId();

        // Act
        materialService.deleteById(id);

        // Assert
        assertThrows(ResourceNotFoundException.class, () -> materialService.findById(id));
    }

    @Test
        // Test to verify that a school material has been successfully deleted with a invalid ID
    void testDeleteMaterial_WhenIdDoesNotExist() {
        // Arrange
        Long nonexistentId = 999L;

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> materialService.deleteById(nonexistentId)
        );

        assertEquals("Material with ID 999 not found", exception.getMessage());
        System.out.println("Exception message: " + exception.getMessage());
    }




}
