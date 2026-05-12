package com.ouss.web.service.queue;

import org.springframework.stereotype.Component;

@Component
public class RabbitMQMock implements MessageService{
    @Override
    public void sendAccountCreation(String email) {
        System.out.println("sendAccountCreation");
    }
}
