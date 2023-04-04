package br.com.compass.party.framework.adapters.in.event.topic.listener;

import br.com.compass.party.application.ports.in.PartyUseCase;
import br.com.compass.party.domain.dto.AssociationDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private PartyUseCase useCase;
    @KafkaListener(topics = "${topic.party-topic}", groupId = "group_id")
    public void Consumer(ConsumerRecord<String, String> payload) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        var associationDTO = mapper.readValue(payload.value(), AssociationDTO.class);
        log.info("Message consumed!");

        useCase.deleteAssociation(associationDTO);
    }
}
