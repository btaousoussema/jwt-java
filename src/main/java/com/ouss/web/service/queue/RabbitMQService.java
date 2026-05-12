package com.ouss.web.service.queue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("Prod")
public class RabbitMQService implements MessageService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendAccountCreation(String email) {
        if(email == null) return;
        rabbitTemplate.convertAndSend("emails", email);
    }
}
