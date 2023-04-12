package br.com.compass.associate.framework.adapters.in.rest;

import br.com.compass.associate.application.service.AssociateService;
import br.com.compass.associate.framework.adapters.out.partyClient.PartyClient;
import br.com.compass.associate.util.AssociateMocks;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class AssociateControllerTest {
    @MockBean
    private AssociateService associateService;
    @MockBean
    private PartyClient partyClient;
    @Autowired
    private MockMvc mockMvc;
    @Spy
    private ObjectMapper objectMapper;

    private final String URL = "/associates";

    private final Long ID = 1l;

    private final String URL_ID = URL + "/" + ID;

    private static final String ID_PARTY = "p416203";

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void whenCreateAssociateReturnSuces() throws Exception {
        var associateDTO = AssociateMocks.getAssociateDTO();
        var associateResponse = AssociateMocks.getAssociateResponse();

        when(associateService.createAssociate(associateDTO)).thenReturn(associateResponse);

        var request = objectMapper.writeValueAsString(associateDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }
    @Test
    void whenUpdateReturnSucess() throws Exception {
        var associateDTO = AssociateMocks.getAssociateDTO();
        var associateResponse = AssociateMocks.getAssociateResponse();

        when(associateService.update(ID, associateDTO)).thenReturn(associateResponse);

        var request = objectMapper.writeValueAsString(associateDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(URL_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    @Test
    void findByIdTest() throws Exception {
        var associateResponse = AssociateMocks.getAssociateResponse();

        when(associateService.findById(any())).thenReturn(associateResponse);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());

    }
    @Test
    void findAllTest() throws Exception {
        var pageableResponse = AssociateMocks.getPageableResponse();

        when(associateService.findAll(any(), any())).thenReturn(pageableResponse);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void deleteTest() throws Exception {
        doNothing().when(associateService).delete(ID);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(URL_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }
    @Test
    void getParties() throws Exception {
        var partyPageable = AssociateMocks.getPageablePartyResponse();

        when(partyClient.findAll()).thenReturn(partyPageable);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URL + "/parties")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    @Test
    void bindAssociationTest() throws Exception {
        var associateResponse = AssociateMocks.getAssociateResponse();
        var associationDTO = AssociateMocks.getAssociationDTO();
        when(associateService.bindAssociate(associationDTO)).thenReturn(associateResponse);

        var request = objectMapper.writeValueAsString(associationDTO);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(URL + "/parties")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void removeAssociation() throws Exception {
        var associateResponse = AssociateMocks.getAssociateResponse();
        when(associateService.removeAssociation(ID, ID_PARTY)).thenReturn(associateResponse);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(URL_ID +"/parties/"+ID_PARTY)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());

    }


}
