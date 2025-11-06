package com.materials.school.messaging;

import com.materials.school.config.RabbitMQConfig;
import com.materials.school.dto.MaterialDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MaterialConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receiveMaterial(MaterialDTO materialDTO) {
        System.out.println("Received material: " + materialDTO.getName());
        // Aqui você pode salvar no banco, validar, etc.
    }
}
