package br.com.compass.party.application.service;

import br.com.compass.party.application.ports.in.PartyUseCase;
import br.com.compass.party.application.ports.out.AssociatePortOut;
import br.com.compass.party.application.ports.out.PartyPortOut;
import br.com.compass.party.application.service.utils.MapperUtils;
import br.com.compass.party.domain.dto.*;
import br.com.compass.party.domain.enums.Ideology;
import br.com.compass.party.domain.model.Associate;
import br.com.compass.party.domain.model.Party;
import br.com.compass.party.framework.adapters.in.event.topic.listener.KafkaConsumer;
import br.com.compass.party.framework.adapters.out.event.topic.KafkaProducer;
import br.com.compass.party.framework.exception.RequestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PartyService implements PartyUseCase {

    private final ModelMapper mapper;
    private final PartyPortOut portOut;

    private final KafkaProducer kafkaProducer;

    private final ObjectMapper objectMapper;

    private final AssociatePortOut associatePortOut;

    private final MapperUtils mapperUtils;
    @Override
    public PartyResponse createParty(PartyDTO partyDTO) {
        if(partyDTO.getFoundationDate().isAfter(LocalDate.now())){
            throw new RequestException("invalid foundationDate", HttpStatus.BAD_REQUEST);
        }

        var party = mapperUtils.dtoToPartyModel(partyDTO);

        party.setIdParty(generateID());
        portOut.save(party);

        return mapperUtils.modelToPartyResponse(party);
    }

    @Override
    public PageableResponse findAll(Ideology ideology, Pageable pageable){
        Page<Party> page = ideology == null ?
                portOut.findAll(pageable) :
                portOut.findByIdeology(ideology, pageable);
        if(page.isEmpty()){
            throw new RequestException("Parties with this ideology not found!", HttpStatus.BAD_REQUEST);
        }

        return PageableResponse.builder()
                .numberOfElements(page.getNumberOfElements())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .parties(page.getContent())
                .build();
    }
    @Override
    public PartyResponse findById(String id){
        return mapperUtils.modelToPartyResponse(getParty(id));
    }

    @Override
    public PartyResponse update(String id, PartyDTO partyDTO) throws JsonProcessingException {

        if(partyDTO.getFoundationDate().isAfter(LocalDate.now())){
            throw new RequestException("invalid foundationDate", HttpStatus.BAD_REQUEST);
        }

        var party = getParty(id);

        mapperUtils.updatePartyMapping(party, partyDTO);

        portOut.save(party);

        if(!party.getAssociates().isEmpty()){
            String message = objectMapper.writeValueAsString(party);
            kafkaProducer.sendMessage(message);
        }

        return mapperUtils.modelToPartyResponse(party);
    }



    @Override
    public void delete(String id) {
        getParty(id);
        portOut.deleteById(id);
    }

    @Override
    public PartyResponse bindAssociation(Associate associate, String idParty) {
        var party = getParty(idParty);

        party.getAssociates().add(associate);
        portOut.save(party);

        return mapperUtils.modelToPartyResponse(party);
    }

    @Override
    public void deleteAssociation(AssociationDTO associationDTO) {
        var party = getParty(associationDTO.getIdParty());

        List<Associate> associates = party.getAssociates();

        for(Associate associate : associates){
            if(associate.getId() == associationDTO.getIdAssociate()){
                associates.remove(associate);

                party.setAssociates(associates);
                portOut.save(party);
                break;
            }
        }
    }

    @Override
    public List<AssociateResponse> getAffiliates(String id) {
        var party = getParty(id);
        var associates = party.getAssociates();

        var response = mapperUtils.listAssociatestoListResponse(associates);
        if(response.isEmpty()){
            throw new RequestException("This party has no associates", HttpStatus.BAD_REQUEST);
        }
        return response;

    }

    @Override
    public void updateAssociate(Associate associate) {
        associatePortOut.save(associate);
    }

    private Party getParty(String id){
        return portOut.findById(id)
                .orElseThrow(()-> new RequestException("Party with this id not found", HttpStatus.BAD_REQUEST));
    }

    private String generateID(){
        Random generator = new Random();
        StringBuilder id = new StringBuilder("p");
        for(int i = 0; i < 5; i++){
            id.append(generator.nextInt(10));
        }
        return id.toString();
    }


}
