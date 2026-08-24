package com.ouss.web.service.queue;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Profile("Production")
@Service
public class KafkaMessageService implements MessageService {

    @Autowired
    private KafkaTemplate<String, String> kafkaProducer;

    @Override
    public void sendEmailAccountCreation(String email) {
        if(email == null) return;
        kafkaProducer.send(new ProducerRecord<>("accountcreated", email));
    }
}
