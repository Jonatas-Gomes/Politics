package br.com.compass.associate.framework.adapters.event.topic.listener;

import br.com.compass.associate.application.ports.in.AssociateUseCase;
import br.com.compass.associate.domain.model.Party;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class KafkaConsumer {
    private final AssociateUseCase useCase;
    private final ObjectMapper mapper;
    @KafkaListener(topics = "${topic.update-topic}", groupId = "grouptwo_id")
    public void Consumer(ConsumerRecord<String, String> payload) throws JsonProcessingException {

        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibility(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));

        var party = mapper.readValue(payload.value(), Party.class);
        log.info("Message consumed!, update party");

        useCase.updateParty(party);



    }
}
