package br.com.compass.associate.application;

import br.com.compass.associate.application.ports.out.AssociatePortOut;
import br.com.compass.associate.application.ports.out.PartyPortOut;
import br.com.compass.associate.application.service.AssociateService;
import br.com.compass.associate.domain.dto.AssociateResponse;
import br.com.compass.associate.domain.model.Associate;
import br.com.compass.associate.framework.adapters.out.event.topic.KafkaProducer;
import br.com.compass.associate.framework.adapters.out.partyClient.PartyClient;
import br.com.compass.associate.framework.exception.RequestException;
import br.com.compass.associate.util.AssociateMocks;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssociateServiceTest {
    @InjectMocks
    private AssociateService service;
    @Mock
    private AssociatePortOut portOut;
    @Mock
    private PartyPortOut partyPortOut;
    @Mock
    private PartyClient client;
    @Mock
    private KafkaProducer kafkaProducer;
    @Spy
    private ModelMapper mapper;
    @Mock
    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp(){
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    @Test
    void whenCreateAssociateReturnSucess(){
        var associateDTO = AssociateMocks.getAssociateDTO();
        var associateResponse = AssociateMocks.getAssociateResponse();
        var associate = AssociateMocks.getAssociate();

        when(mapper.map(associate, AssociateResponse.class)).thenReturn(associateResponse);

        when(portOut.save(associate)).thenReturn(associate);

        var response = service.createAssociate(associateDTO);

        verify(portOut).save(associate);

        Assertions.assertEquals(associate.getFullName(), response.getFullName());
    }
    @Test
    void whenUpdateWithIdValidReturnSucess() throws JsonProcessingException {
        var associateDTO = AssociateMocks.getAssociateDTO();
        var associateResponse = AssociateMocks.getAssociateResponse();
        var associate = AssociateMocks.getAssociate();

        when(mapper.map(associate, AssociateResponse.class)).thenReturn(associateResponse);

        when(portOut.findById(1l)).thenReturn(Optional.of(associate));
        when(portOut.save(associate)).thenReturn(associate);

        var response = service.update(1l, associateDTO);

        verify(portOut).save(associate);

        Assertions.assertEquals(associate.getFullName(), response.getFullName());

    }
    @Test
    void should_Exception_When_TryUpdate_With_IdNonexistent(){
        var associateDTO = AssociateMocks.getAssociateDTO();
        when(portOut.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(RequestException.class, () ->{
            service.update(2l,associateDTO);
        });
    }
    @Test
    void when_Update_Associate_WithParty_Field_NotNull_Should_Sucess_And_SendMessage() throws JsonProcessingException {
        var associateDTO = AssociateMocks.getAssociateDTO();
        var associateResponse = AssociateMocks.getAssociateResponse();
        var associate = AssociateMocks.getAssociateWithParty();

        when(portOut.findById(1l)).thenReturn(Optional.of(associate));
        when(portOut.save(associate)).thenReturn(associate);

        doNothing().when(kafkaProducer).sendMessage(any(), any());

        service.update(1l, associateDTO);

        verify(kafkaProducer).sendMessage(any(),any());



    }

}
