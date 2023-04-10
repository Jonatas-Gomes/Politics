package br.com.compass.party.framework.adapters.in.event.topic.listener;

import br.com.compass.party.application.ports.in.PartyUseCase;
import br.com.compass.party.domain.dto.AssociationDTO;
import br.com.compass.party.domain.model.Associate;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateConsumer {

    private final PartyUseCase useCase;
    private final ObjectMapper mapper;
    @KafkaListener(topics = "${topic.update-associate}", groupId = "groupthree_id")
    public void Consumer(ConsumerRecord<String, String> payload) throws JsonProcessingException {

        var associate = mapper.readValue(payload.value(), Associate.class);
        log.info("Message consumed!");

        useCase.updateAssociate(associate);
        log.info("Update sucesful!");
    }
}
