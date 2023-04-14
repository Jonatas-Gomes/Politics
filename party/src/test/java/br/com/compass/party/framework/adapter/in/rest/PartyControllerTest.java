package br.com.compass.party.framework.adapter.in.rest;

import br.com.compass.party.application.service.PartyService;
import br.com.compass.party.util.PartyMocks;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
public class PartyControllerTest {
    @MockBean
    private PartyService partyService;
    @Autowired
    private MockMvc mockMvc;
    @Spy
    private ObjectMapper mapper;

    private final String URL = "/parties";
    private final String ID = "p416203";
    private final Long ID_ASSOCIATE = 1L;
    private final String URL_ID = URL+ "/"+ ID;

    @BeforeEach
    void setUp(){
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    @Test
    void createPartyTest() throws Exception {
        var partyDTO = PartyMocks.getPartyDTO();
        var partyResponse = PartyMocks.getPartyResponse();

        when(partyService.createParty(partyDTO)).thenReturn(partyResponse);
        var request = mapper.writeValueAsString(partyDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .content(request)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }
    @Test
    void findAllTest() throws Exception {
        var pageableResponse = PartyMocks.getPageableResponse();

        when(partyService.findAll(any(),any())).thenReturn(pageableResponse);


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    @Test
    void findByIdTest() throws Exception {
        var partyResponse = PartyMocks.getPartyResponse();

        when(partyService.findById(ID)).thenReturn(partyResponse);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    @Test
    void updateTest() throws Exception {
        var partyDTO = PartyMocks.getPartyDTO();
        var partyResponse = PartyMocks.getPartyResponse();

        when(partyService.update(ID, partyDTO)).thenReturn(partyResponse);

        var request = mapper.writeValueAsString(partyDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(URL_ID)
                        .content(request)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    @Test
    void deleteTest() throws Exception {
        doNothing().when(partyService).delete(ID);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(URL_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }
    @Test
    void bindAssociation() throws Exception {
        var associate = PartyMocks.getAssociate();
        var partyResponse = PartyMocks.getPartyResponse();

        when(partyService.bindAssociation(associate, ID)).thenReturn(partyResponse);

        var request = mapper.writeValueAsString(associate);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL + "/associates/"+ID)
                        .content(request)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    @Test
    void getAffiliates() throws Exception {
        var associateResponse = PartyMocks.getAssociateResponse();

        when(partyService.getAffiliates(ID)).thenReturn(List.of(associateResponse));


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL_ID + "/associates")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
