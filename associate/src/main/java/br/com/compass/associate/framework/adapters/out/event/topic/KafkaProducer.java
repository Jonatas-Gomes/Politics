package br.com.compass.associate.framework.adapters.out.event.topic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {
   // @Value("${topic.associate-topic}")
    //private String topicName;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message, String topicName){
        kafkaTemplate.send(topicName, message);
        log.info("message sent, AssociateProducer");
    }


}
