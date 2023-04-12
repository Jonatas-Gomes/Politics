package br.com.compass.associate.application.service;

import br.com.compass.associate.application.ports.in.AssociateUseCase;
import br.com.compass.associate.application.ports.out.AssociatePortOut;
import br.com.compass.associate.application.ports.out.PartyPortOut;
import br.com.compass.associate.application.service.utils.MapperUtils;
import br.com.compass.associate.domain.dto.AssociateDTO;
import br.com.compass.associate.domain.dto.AssociateResponse;
import br.com.compass.associate.domain.dto.AssociationDTO;
import br.com.compass.associate.domain.dto.PageableResponse;
import br.com.compass.associate.domain.enums.PoliticalOffice;
import br.com.compass.associate.domain.model.Associate;
import br.com.compass.associate.domain.model.Party;
import br.com.compass.associate.framework.adapters.out.event.topic.KafkaProducer;
import br.com.compass.associate.framework.adapters.out.partyClient.PartyClient;
import br.com.compass.associate.framework.exception.RequestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssociateService implements AssociateUseCase{

    private final AssociatePortOut portOut;
    private final PartyClient partyClient;
    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;
    private final PartyPortOut partyPortOut;

    private final MapperUtils mapperUtils;

    @Override
    public AssociateResponse createAssociate(AssociateDTO associateDTO) {
        if(associateDTO.getBirthday().isAfter(LocalDate.now())){
            throw new RequestException("invalid birthday", HttpStatus.BAD_REQUEST);
        }
        var associate = mapperUtils.mapAssociateDtoToAssociate(associateDTO);
        portOut.save(associate);
        return mapperUtils.mapAssociateToAssociateResponse(associate);

    }

    @Override
    public PageableResponse findAll(PoliticalOffice politicalOffice, Pageable pageable) {
        Page<Associate>page = politicalOffice == null ?
                portOut.findAll(pageable):
                portOut.findByPoliticalOffice(politicalOffice, pageable);

        if(page.isEmpty()){
            throw new RequestException("Associate not found", HttpStatus.BAD_REQUEST);
        }

        return PageableResponse.builder()
                .numberOfElements(page.getNumberOfElements())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .associates(page.getContent())
                .build();

    }

    @Override
    public AssociateResponse findById(Long id) {
        var associate =getAssociate(id);
        return mapperUtils.mapAssociateToAssociateResponse(associate);
    }

    @Override
    public void delete(Long id) {
        getAssociate(id);
        portOut.deleteById(id);
    }

    @Override
    public AssociateResponse update(Long id, AssociateDTO associateDTO) throws JsonProcessingException {
        var associate = getAssociate(id);

        if(associateDTO.getBirthday().isAfter(LocalDate.now())){
            throw new RequestException("invalid birthday", HttpStatus.BAD_REQUEST);
        }

        mapperUtils.associateUpdateMapping(associate, associateDTO);

        portOut.save(associate);

        if(associate.getParty()!= null){
            String message = objectMapper.writeValueAsString(associate);
            kafkaProducer.sendMessage(message, "update_associate");
        }

        return mapperUtils.mapAssociateToAssociateResponse(associate);
    }
    @Override

    public AssociateResponse bindAssociate(AssociationDTO associationDTO){
        var associate = getAssociate(associationDTO.getIdAssociate());

        if(associate.getParty() == null){
            var party = Optional.of(partyClient.bindAssociation(associate, associationDTO.getIdParty()));
            associate.setParty(party.get());
            portOut.save(associate);
        }else{
            throw new RequestException("This associate is already affiliated to a party", HttpStatus.BAD_REQUEST);
        }
        return mapperUtils.mapAssociateToAssociateResponse(associate);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public AssociateResponse removeAssociation(Long idAssociate, String idParty) throws JsonProcessingException {
        var associate = getAssociate(idAssociate);

        if(associate.getParty() != null){

            if(associate.getParty().getIdParty().equals(idParty)){
                associate.setParty(null);
                var associationDTO = mapperUtils.associationDTOBuilder(idAssociate, idParty);

                String message = objectMapper.writeValueAsString(associationDTO);
                kafkaProducer.sendMessage(message, "remove_association");
                
                portOut.save(associate);
            }
            else{
                throw new RequestException("This associate is not associated with this party", HttpStatus.BAD_REQUEST);
            }
        }else{
            throw new RequestException("This associate is not affiliated with any party",HttpStatus.BAD_REQUEST);
        }

        return mapperUtils.mapAssociateToAssociateResponse(associate);
    }

    @Override
    public void updateParty(Party party) {
        partyPortOut.save(party);
    }

    private Associate getAssociate(Long id){
        return portOut.findById(id)
                .orElseThrow(() -> new RequestException("Associate not found!", HttpStatus.BAD_REQUEST));
    }


}
