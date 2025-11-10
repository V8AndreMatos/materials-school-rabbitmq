package com.materials.school.integration;

import com.materials.school.dto.MaterialDTO;
import com.materials.school.service.MaterialService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class MaterialServiceRabbitTest {

    @Autowired
    private MaterialService materialService;

    @Test
    void testCreateMaterial_SendsRealRabbitMessage() {
        // Arrange
        MaterialDTO dto = new MaterialDTO();
        dto.setName("Musical Material");

        // Act
        MaterialDTO result = materialService.create(dto);

        // Assert
        assertNotNull(result);
        assertEquals("Musical Material", result.getName());
        assertNotNull(result.getId());

        System.out.println("Material criado e enviado para RabbitMQ: " + result);
    }
}
