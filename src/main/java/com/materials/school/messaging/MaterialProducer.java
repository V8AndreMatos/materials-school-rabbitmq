package com.materials.school.messaging;

import com.materials.school.config.RabbitMQConfig;
import com.materials.school.dto.MaterialDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class MaterialProducer {

    private final RabbitTemplate rabbitTemplate;

    public MaterialProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMaterial(MaterialDTO materialDTO) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE, materialDTO);
    }
}

