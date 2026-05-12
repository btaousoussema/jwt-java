package com.ouss.web.service.queue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("PROD")
public class RabbitMQConsumer {

    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "emails")
    private void consume(String message) {
        System.out.println("Receive this message: " + message);
    }
}
