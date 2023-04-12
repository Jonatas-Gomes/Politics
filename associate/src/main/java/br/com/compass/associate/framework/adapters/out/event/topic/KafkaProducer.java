package br.com.compass.associate.framework.adapters.out.event.topic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message, String topicName){
        kafkaTemplate.send(topicName, message);
        log.info("message sent, AssociateProducer");
    }


}
