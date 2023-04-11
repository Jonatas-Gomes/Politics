package br.com.compass.associate.application;

import br.com.compass.associate.application.ports.out.AssociatePortOut;
import br.com.compass.associate.application.ports.out.PartyPortOut;
import br.com.compass.associate.application.service.AssociateService;
import br.com.compass.associate.domain.dto.AssociateResponse;
import br.com.compass.associate.domain.dto.PageableResponse;
import br.com.compass.associate.domain.enums.PoliticalOffice;
import br.com.compass.associate.domain.model.Associate;
import br.com.compass.associate.framework.adapters.out.event.topic.KafkaProducer;
import br.com.compass.associate.framework.adapters.out.partyClient.PartyClient;
import br.com.compass.associate.framework.exception.RequestException;
import br.com.compass.associate.util.AssociateMocks;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.impl.execchain.RequestAbortedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
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

    private static final Long ID = 1l;
    private static final String ID_PARTY = "p416203";
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
        when(mapper.map(associateDTO, Associate.class)).thenReturn(associate);

        when(portOut.save(associate)).thenReturn(associate);

        var response = service.createAssociate(associateDTO);

        verify(portOut).save(associate);

        Assertions.assertEquals(associate.getFullName(), response.getFullName());
    }
    @Test
    void shouldRequestExceptionWhenTryCreateAssociateWithDateInvalid(){
        var associateDTO = AssociateMocks.getAssociateDTO();
        associateDTO.setBirthday(LocalDate.of(2025, 12, 31));

        Assertions.assertThrows(RequestException.class, ()->{
           service.createAssociate(associateDTO);
        });
    }
    @Test
    void whenUpdateWithIdValidReturnSucess() throws JsonProcessingException {
        var associateDTO = AssociateMocks.getAssociateDTO();
        var associateResponse = AssociateMocks.getAssociateResponse();
        var associate = AssociateMocks.getAssociate();

        when(mapper.map(associate, AssociateResponse.class)).thenReturn(associateResponse);

        when(portOut.findById(ID)).thenReturn(Optional.of(associate));
        when(portOut.save(associate)).thenReturn(associate);

        var response = service.update(ID, associateDTO);

        verify(portOut).save(associate);

        Assertions.assertEquals(associate.getFullName(), response.getFullName());

    }
    @Test
    void should_Exception_When_TryUpdate_With_IdNonexistent(){
        var associateDTO = AssociateMocks.getAssociateDTO();
        when(portOut.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(RequestException.class, () ->{
            service.update(ID,associateDTO);
        });
    }
    @Test
    void should_RequestExceptionWhenTryUpdateAssociateWithBirthDayInvalid(){
        var associateDTO = AssociateMocks.getAssociateDTO();
        associateDTO.setBirthday(LocalDate.of(2025, 12, 31));
        var associate = AssociateMocks.getAssociate();

        when(portOut.findById(ID)).thenReturn(Optional.of(associate));

        Assertions.assertThrows(RequestException.class, ()->{
            service.update(ID,associateDTO);
        });
    }
    @Test
    void when_Update_Associate_WithParty_Field_NotNull_Should_Sucess_And_SendMessage() throws JsonProcessingException {
        var associateDTO = AssociateMocks.getAssociateDTO();
        var associateResponse = AssociateMocks.getAssociateResponse();
        var associate = AssociateMocks.getAssociateWithParty();

        when(mapper.map(associate, AssociateResponse.class)).thenReturn(associateResponse);
        when(portOut.findById(ID)).thenReturn(Optional.of(associate));
        when(portOut.save(associate)).thenReturn(associate);

        doNothing().when(kafkaProducer).sendMessage(any(), any());

        var response = service.update(ID, associateDTO);

        verify(kafkaProducer).sendMessage(any(),any());

        Assertions.assertEquals(associate.getFullName(), response.getFullName());
    }
    @Test
    void findByIdTest(){
        var associate = AssociateMocks.getAssociate();
        var associateResponse = AssociateMocks.getAssociateResponse();
        when(portOut.findById(ID)).thenReturn(Optional.of(associate));
        when(mapper.map(associate, AssociateResponse.class)).thenReturn(associateResponse);

        var response = service.findById(ID);

        Assertions.assertEquals(associate.getFullName(), response.getFullName());
    }
    @Test
    void findByIdWithNonexistentIdTest(){
        when(portOut.findById(ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(RequestException.class, () ->{
            service.delete(ID);
        });
    }
    @Test
    void findAllTestWithoutParam(){
        Pageable pageable = mock(Pageable.class);
        Page<Associate> page = mock(Page.class);
        var associate = AssociateMocks.getAssociate();

        when(page.getTotalPages()).thenReturn(1);
        when(page.getTotalElements()).thenReturn(1l);
        when(page.getNumberOfElements()).thenReturn(1);
        when(page.getContent()).thenReturn(List.of(associate));

        when(portOut.findAll(pageable)).thenReturn(page);
        PageableResponse response = service.findAll(null, pageable);

        Assertions.assertEquals(1, response.getTotalPages());
        Assertions.assertEquals(1l, response.getTotalElements());
        Assertions.assertEquals(1, response.getNumberOfElements());

        Assertions.assertEquals(associate.getId(), response.getAssociates().get(0).getId());
    }
    @Test
    void findAllTestWithPoliticalOfficeParam(){
        Pageable pageable = mock(Pageable.class);
        Page<Associate> page = mock(Page.class);
        var associate = AssociateMocks.getAssociate();

        when(page.getTotalPages()).thenReturn(1);
        when(page.getTotalElements()).thenReturn(1l);
        when(page.getNumberOfElements()).thenReturn(1);
        when(page.getContent()).thenReturn(List.of(associate));

        when(portOut.findByPoliticalOffice(PoliticalOffice.President, pageable)).thenReturn(page);
        PageableResponse response = service.findAll(PoliticalOffice.President, pageable);

        Assertions.assertEquals(1, response.getTotalPages());
        Assertions.assertEquals(1l, response.getTotalElements());
        Assertions.assertEquals(1, response.getNumberOfElements());

        Assertions.assertEquals(PoliticalOffice.President, response.getAssociates().get(0).getPoliticalOffice());
    }
    @Test
    void findAllThrowRequestExceptionWhenNotFoundResults(){
        Pageable pageable = mock(Pageable.class);
        Page<Associate> page = Page.empty();

        when(portOut.findAll(pageable)).thenReturn(page);

        Assertions.assertThrows(RequestException.class, () ->{
            service.findAll(null, pageable);
        });
    }
    @Test
    void deleteTest(){
        var associate = AssociateMocks.getAssociate();
        when(portOut.findById(anyLong())).thenReturn(Optional.of(associate));

        doNothing().when(portOut).deleteById(anyLong());
        service.delete(ID);
        verify(portOut).deleteById(anyLong());
    }
    @Test
    void should_Request_Exception_WhenTryDelete_With_IdNonexistent(){
        when(portOut.findById(ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(RequestException.class, () ->{
            service.delete(ID);
        });
    }
    @Test
    void BindAssociateTest(){
        var associate = AssociateMocks.getAssociate();
        var associareResponse = AssociateMocks.getAssociateResponse();
        var party = AssociateMocks.getParty();
        var associationDTO = AssociateMocks.getAssociationDTO();

        when(mapper.map(associate, AssociateResponse.class)).thenReturn(associareResponse);
        when(portOut.findById(anyLong())).thenReturn(Optional.of(associate));
        when(client.bindAssociation(associate, ID_PARTY)).thenReturn(party);
        when(portOut.save(associate)).thenReturn(associate);

        var response = service.bindAssociate(associationDTO);
        verify(portOut).save(associate);

        Assertions.assertEquals(associate.getId(), response.getId());
    }
    @Test
    void shouldRequestExceptionWhenTryBindAssociateWithIdAssociateNonExistent(){
        var associationDTO = AssociateMocks.getAssociationDTO();

        when(portOut.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(RequestException.class, ()->{
           service.bindAssociate(associationDTO);
        });
    }
    @Test
    void shouldRequestExceptionWhenTryBindAssociateWithPartyFieldNotNull(){
        var associate = AssociateMocks.getAssociateWithParty();
        var associationDTO = AssociateMocks.getAssociationDTO();

        when(portOut.findById(anyLong())).thenReturn(Optional.of(associate));

        Assertions.assertThrows(RequestException.class, () ->{
            service.bindAssociate(associationDTO);
        });
    }
    @Test
    void removeAssociationTest() throws JsonProcessingException {
        var associateWithParty = AssociateMocks.getAssociateWithParty();
        var associate = AssociateMocks.getAssociate();

        when(portOut.save(associateWithParty)).thenReturn(associate);
        when(portOut.findById(anyLong())).thenReturn(Optional.of(associateWithParty));
        doNothing().when(kafkaProducer).sendMessage(any(), any());

        var response = service.removeAssociation(ID, ID_PARTY);

        verify(portOut).save(associate);
        Assertions.assertNull(response.getParty());
    }
    @Test
    void tryRemoveAssociationWithPartyFieldNull(){
        var associate = AssociateMocks.getAssociate();

        when(portOut.findById(anyLong())).thenReturn(Optional.of(associate));

        Assertions.assertThrows(RequestException.class, () ->{
           service.removeAssociation(ID, ID_PARTY);
        });
    }
    @Test
    void Try_Remove_Association_With_Associate_IsAlready_Associated_With_A_Party() throws JsonProcessingException {
        var associateWithParty = AssociateMocks.getAssociateWithParty();
        var associate = AssociateMocks.getAssociate();

        var party = AssociateMocks.getParty();
        party.setIdParty("p512290");
        associate.setParty(party);

        Assertions.assertThrows(RequestException.class, () ->{
            service.removeAssociation(ID, ID_PARTY);
        });
    }
    @Test
    void updatePartyTest(){
        var party = AssociateMocks.getParty();
        when(partyPortOut.save(party)).thenReturn(party);

        service.updateParty(party);

        verify(partyPortOut).save(party);
    }


}
