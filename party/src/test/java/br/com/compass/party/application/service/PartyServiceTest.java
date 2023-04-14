package br.com.compass.party.application.service;

import br.com.compass.party.application.ports.out.AssociatePortOut;
import br.com.compass.party.application.ports.out.PartyPortOut;
import br.com.compass.party.application.service.utils.MapperUtils;
import br.com.compass.party.domain.dto.AssociateResponse;
import br.com.compass.party.domain.enums.Ideology;
import br.com.compass.party.domain.model.Associate;
import br.com.compass.party.domain.model.Party;
import br.com.compass.party.framework.adapters.out.event.topic.KafkaProducer;
import br.com.compass.party.framework.exception.RequestException;
import br.com.compass.party.util.PartyMocks;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;
import org.yaml.snakeyaml.events.Event;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PartyServiceTest {
    @InjectMocks
    private PartyService partyService;
    @Mock
    private PartyPortOut partyPortOut;
    @Mock
    private AssociatePortOut associatePortOut;
    @Mock
    private KafkaProducer kafkaProducer;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private MapperUtils mapperUtils;

    private static final String ID_PARTY = "p416203";

    @BeforeEach
    void setUp(){
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    @Test
    void createPartyTest(){
        var partyDTO = PartyMocks.getPartyDTO();
        var party = PartyMocks.getParty();
        var partyResponse = PartyMocks.getPartyResponse();

        when(mapperUtils.dtoToPartyModel(partyDTO)).thenReturn(party);
        when(mapperUtils.modelToPartyResponse(party)).thenReturn(partyResponse);
        when(partyPortOut.save(party)).thenReturn(party);

        var response = partyService.createParty(partyDTO);

        verify(partyPortOut).save(party);
        verify(mapperUtils).modelToPartyResponse(party);
        verify(mapperUtils).dtoToPartyModel(partyDTO);
        Assertions.assertEquals(party.getPartyName(), response.getPartyName());
    }
    @Test
    void sholdRequestExceptionWhenTryCreatePartyWithInvalidDate(){
        var partyDTO = PartyMocks.getPartyDTO();
        partyDTO.setFoundationDate(LocalDate.of(2025, 12, 23));

        Assertions.assertThrows(RequestException.class, () ->{
            partyService.createParty(partyDTO);
        });
    }
    @Test
    void findAllWithoutParamTest(){
        Pageable pageable = mock(Pageable.class);
        Page<Party> page = mock(Page.class);
        var party = PartyMocks.getParty();


        when(page.getContent()).thenReturn(List.of(party));
        when(page.getTotalPages()).thenReturn(1);
        when(page.getTotalElements()).thenReturn(1l);
        when(page.getNumberOfElements()).thenReturn(1);

        when(partyPortOut.findAll(pageable)).thenReturn(page);
        var response = partyService.findAll(null, pageable);

        Assertions.assertEquals(1, response.getTotalPages());
        Assertions.assertEquals(1l, response.getTotalElements());
        Assertions.assertEquals(1, response.getNumberOfElements());

        verify(partyPortOut).findAll(any());
        Assertions.assertEquals(party.getPartyName(), response.getParties().get(0).getPartyName());
        Assertions.assertEquals(party.getIdParty(), response.getParties().get(0).getIdParty());
    }
    @Test
    void findAllWithIdeologyParam(){
        Pageable pageable = mock(Pageable.class);
        Page<Party> page = mock(Page.class);
        var party = PartyMocks.getParty();

        when(page.getContent()).thenReturn(List.of(party));
        when(page.getTotalPages()).thenReturn(1);
        when(page.getTotalElements()).thenReturn(1l);
        when(page.getNumberOfElements()).thenReturn(1);

        when(partyPortOut.findByIdeology(Ideology.Center, pageable)).thenReturn(page);
        var response = partyService.findAll(Ideology.Center, pageable);

        Assertions.assertEquals(1, response.getTotalPages());
        Assertions.assertEquals(1l, response.getTotalElements());
        Assertions.assertEquals(1, response.getNumberOfElements());

        verify(partyPortOut).findByIdeology(any(), any());
        Assertions.assertEquals(party.getIdeology(), response.getParties().get(0).getIdeology());

    }
    @Test
    void throw_Exception_When_PartyPortOut_ReturnPageEmpty(){
        Pageable pageable = mock(Pageable.class);
        Page<Party> page = Page.empty();

        when(partyPortOut.findAll(pageable)).thenReturn(page);

        Assertions.assertThrows(RequestException.class, () ->{
            partyService.findAll(null, pageable);
        });
    }
    @Test
    void findByIdTest(){
        var party = PartyMocks.getParty();
        var partyResponse = PartyMocks.getPartyResponse();

        when(partyPortOut.findById(ID_PARTY)).thenReturn(Optional.of(party));
        when(mapperUtils.modelToPartyResponse(party)).thenReturn(partyResponse);

        var response = partyService.findById(ID_PARTY);

        verify(mapperUtils).modelToPartyResponse(party);
        verify(partyPortOut).findById(ID_PARTY);
        Assertions.assertEquals(party.getIdParty(), response.getIdParty());
    }
    @Test
    void throwExceptionWhenFindByIdNonexistent(){
        var party = PartyMocks.getParty();
        when(partyPortOut.findById("p401820")).thenReturn(Optional.empty());

        Assertions.assertThrows(RequestException.class,() ->{
            partyService.findById("p401820");
        });
    }
    @Test
    void updateTest() throws JsonProcessingException {
        var partyDTO = PartyMocks.getPartyDTO();
        var party = PartyMocks.getParty();
        var partyResponse = PartyMocks.getPartyResponse();
        party.setAssociates(new ArrayList<>());

        when(partyPortOut.findById(ID_PARTY)).thenReturn(Optional.of(party));
        when(mapperUtils.updatePartyMapping(party, partyDTO)).thenReturn(party);
        when(mapperUtils.modelToPartyResponse(party)).thenReturn(partyResponse);
        when(partyPortOut.save(party)).thenReturn(party);

        var response = partyService.update(ID_PARTY, partyDTO);


        verify(partyPortOut).save(party);
        verify(mapperUtils).modelToPartyResponse(party);
        verify(mapperUtils).updatePartyMapping(party, partyDTO);
        Assertions.assertEquals(party.getPartyName(), response.getPartyName());
    }
    @Test
    void throwRequestExceptionWhenTryUpdateWithInvalidDate(){
        var partyDTO = PartyMocks.getPartyDTO();
        partyDTO.setFoundationDate(LocalDate.of(2025, 12, 23));

        Assertions.assertThrows(RequestException.class, ()->{
            partyService.update(ID_PARTY, partyDTO);
        });
    }
    @Test
    void throwRequestExceptionWhenTryUpdateWithIDNonexistent(){
        var partyDTO = PartyMocks.getPartyDTO();

        when(partyPortOut.findById("p417892")).thenReturn(Optional.empty());

        Assertions.assertThrows(RequestException.class, () ->{
            partyService.update("p417892", partyDTO);
        });
    }
    @Test
    void updateAndSendMessageWhenAssociatesFieldNoEmpty() throws JsonProcessingException {
        var partyDTO = PartyMocks.getPartyDTO();
        var party = PartyMocks.getParty();
        var associate = PartyMocks.getAssociate();
        party.setAssociates(List.of(associate));
        var partyResponse = PartyMocks.getPartyResponse();

        when(partyPortOut.findById(ID_PARTY)).thenReturn(Optional.of(party));
        when(mapperUtils.updatePartyMapping(party, partyDTO)).thenReturn(party);
        when(mapperUtils.modelToPartyResponse(party)).thenReturn(partyResponse);
        when(partyPortOut.save(party)).thenReturn(party);

        var response = partyService.update(ID_PARTY, partyDTO);

        verify(kafkaProducer).sendMessage(any());
        verify(partyPortOut).save(party);
        verify(mapperUtils).modelToPartyResponse(party);
        verify(mapperUtils).updatePartyMapping(party, partyDTO);
        Assertions.assertEquals(party.getPartyName(), response.getPartyName());

    }
    @Test
    void deleteTest(){
        var party = PartyMocks.getParty();

        when(partyPortOut.findById(ID_PARTY)).thenReturn(Optional.of(party));
        doNothing().when(partyPortOut).deleteById(ID_PARTY);

        partyService.delete(ID_PARTY);
        verify(partyPortOut).deleteById(ID_PARTY);
    }
    @Test
    void throwRequestExceptionWhenTryDeleteWithIdNonexistent(){
        when(partyPortOut.findById(ID_PARTY)).thenReturn(Optional.empty());

        Assertions.assertThrows(RequestException.class, () ->{
            partyService.delete(ID_PARTY);
        });
    }
    @Test
    void deleteAssociationTest(){
        var party = PartyMocks.getParty();
        var associationDTO = PartyMocks.getAssociationDTO();
        var associate = PartyMocks.getAssociate();
        ArrayList<Associate> list = new ArrayList<>();
        list.add(associate);
        party.setAssociates(list);

        when(partyPortOut.save(party)).thenReturn(party);
        when(partyPortOut.findById(ID_PARTY)).thenReturn(Optional.of(party));

        partyService.deleteAssociation(associationDTO);
        verify(partyPortOut).save(party);
    }
    @Test
    void throwRequestExceptionWhenTryDeleteAssociationWithPartyIdNonexistent(){
        var associationDTO = PartyMocks.getAssociationDTO();
        when(partyPortOut.findById(ID_PARTY)).thenReturn(Optional.empty());

        Assertions.assertThrows(RequestException.class,() ->{
            partyService.deleteAssociation(associationDTO);
        });
    }
    @Test
    void getAffiliates(){
        var party = PartyMocks.getParty();
        var associate = PartyMocks.getAssociate();
        ArrayList<Associate> list = new ArrayList<>();
        list.add(associate);
        party.setAssociates(list);
        var associateResponse = PartyMocks.getAssociateResponse();

        when(partyPortOut.findById(ID_PARTY)).thenReturn(Optional.of(party));
        when(mapperUtils.listAssociatestoListResponse(list)).thenReturn(List.of(associateResponse));

        var response = partyService.getAffiliates(ID_PARTY);

        verify(partyPortOut).findById(ID_PARTY);

        Assertions.assertEquals(party.getAssociates().get(0).getId(), response.get(0).getId());
    }
    @Test
    void throwRequestExceptionWhenTryGetAffiliatesWithIdPartyNonexistent(){
        when(partyPortOut.findById(ID_PARTY)).thenReturn(Optional.empty());

        Assertions.assertThrows(RequestException.class,() ->{
            partyService.getAffiliates(ID_PARTY);
        });
    }
    @Test
    void throwRequestExceptionWhenGetAffiliatesReturnsEmptyList(){
        var party = PartyMocks.getParty();
        var associateResponse = PartyMocks.getAssociateResponse();
        ArrayList<Associate> list = new ArrayList<>();
        party.setAssociates(list);
        ArrayList<AssociateResponse>listResponse = new ArrayList<>();

        when(partyPortOut.findById(ID_PARTY)).thenReturn(Optional.of(party));
        when(mapperUtils.listAssociatestoListResponse(list)).thenReturn(listResponse);

        Assertions.assertThrows(RequestException.class, ()->{
            partyService.getAffiliates(ID_PARTY);
        });
    }
    @Test
    void bindAssociationTest(){
        var party = PartyMocks.getParty();
        var associate = PartyMocks.getAssociate();
        var partyResponse = PartyMocks.getPartyResponse();
        party.setAssociates(new ArrayList<Associate>());

        when(partyPortOut.findById(ID_PARTY)).thenReturn(Optional.of(party));
        when(partyPortOut.save(party)).thenReturn(party);
        when(mapperUtils.modelToPartyResponse(party)).thenReturn(partyResponse);

        var response = partyService.bindAssociation(associate, ID_PARTY);

        verify(partyPortOut).save(party);
        verify(partyPortOut).findById(ID_PARTY);
        Assertions.assertEquals(party.getPartyName(), response.getPartyName());
    }
    @Test
    void throwRequestExceptionWhenTryBindAssociationWithIdPartyNonexistent(){
        when(partyPortOut.findById(ID_PARTY)).thenReturn(Optional.empty());
        var associate = PartyMocks.getAssociate();

        Assertions.assertThrows(RequestException.class, ()->{
            partyService.bindAssociation(associate, ID_PARTY);
        });
    }
    @Test
    void updateAssociateTest(){
        var associate = PartyMocks.getAssociate();
        when(associatePortOut.save(associate)).thenReturn(associate);

        partyService.updateAssociate(associate);

        verify(associatePortOut).save(associate);
    }
}



