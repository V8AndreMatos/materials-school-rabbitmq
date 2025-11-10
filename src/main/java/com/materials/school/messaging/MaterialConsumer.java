package com.materials.school.messaging;

import com.materials.school.config.RabbitMQConfig;
import com.materials.school.dto.MaterialDTO;
import com.materials.school.entity.Material;
import com.materials.school.repository.MaterialRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MaterialConsumer {

    private final MaterialRepository materialRepository;

    public MaterialConsumer(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receiveMaterial(MaterialDTO materialDTO) {
        try {
            Material entity = materialDTO.toEntity();
            materialRepository.save(entity);
            System.out.println("✅ Material salvo com sucesso: " + entity.getName());
        } catch (Exception e) {
            System.err.println("❌ Erro ao processar mensagem: " + materialDTO);
            throw e; // mensagem vai para a DLQ
        }
    }
}

