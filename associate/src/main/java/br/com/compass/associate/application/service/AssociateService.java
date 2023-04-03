package br.com.compass.associate.application.service;

import br.com.compass.associate.application.ports.in.AssociateUseCase;
import br.com.compass.associate.application.ports.out.AssociatePortOut;
import br.com.compass.associate.domain.dto.AssociateDTO;
import br.com.compass.associate.domain.dto.AssociateResponse;
import br.com.compass.associate.domain.dto.AssociationDTO;
import br.com.compass.associate.domain.dto.PageableResponse;
import br.com.compass.associate.domain.enums.PoliticalOffice;
import br.com.compass.associate.domain.model.Associate;
import br.com.compass.associate.framework.adapters.out.partyClient.PartyClient;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssociateService implements AssociateUseCase{

    private final ModelMapper mapper;
    private final AssociatePortOut portOut;
    private final PartyClient partyClient;
    @Override
    public AssociateResponse createAssociate(AssociateDTO associateDTO) {
        Associate associate = mapper.map(associateDTO, Associate.class);
        portOut.save(associate);
        return mapper.map(associate, AssociateResponse.class);
    }

    @Override
    public PageableResponse findAll(PoliticalOffice politicalOffice, Pageable pageable) {
        Page<Associate>page = politicalOffice == null ?
                portOut.findAll(pageable):
                portOut.findByPoliticalOffice(politicalOffice, pageable);

        if(page.isEmpty()){
            throw new RuntimeException("Associate not found");
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
        return mapper.map(associate, AssociateResponse.class);
    }

    @Override
    public void delete(Long id) {
        getAssociate(id);
        portOut.deleteById(id);
    }

    @Override
    public AssociateResponse update(Long id, AssociateDTO associateDTO) {
        var associate = getAssociate(id);

        associate.setFullName(associateDTO.getFullName());
        associate.setSex(associate.getSex());
        associate.setBirthday(associateDTO.getBirthday());
        associate.setPoliticalOffice(associateDTO.getPoliticalOffice());

        portOut.save(associate);

        return mapper.map(associate, AssociateResponse.class);
    }
    @Override
    public AssociateResponse bindAssociate(AssociationDTO associationDTO){
        var associate = getAssociate(associationDTO.getIdAssociate());

        if(associate.getParty() == null){
            var party = Optional.of(partyClient.bindAssociation(associate, associationDTO.getIdParty()))
                    .orElseThrow(() -> new RuntimeException("Party not found!"));
            associate.setParty(party);
            portOut.save(associate);
        }else{
            throw new RuntimeException("This associate is already affiliated to a party");
        }

        return mapper.map(associate, AssociateResponse.class);
    }

    @Override
    public AssociateResponse removeAssociation(Long idAssociate, String idParty) {
        var associate = getAssociate(idAssociate);

        if(associate.getParty() != null){
            if(associate.getParty().getIdParty() == idParty){
                associate.setParty(null);
                portOut.save(associate);
            }
            else{
                throw new RuntimeException("This associate is not associated with this party");
            }
        }else{
            throw new RuntimeException("This associate is not affiliated with any party");
        }

        return mapper.map(associate, AssociateResponse.class);
    }

    private Associate getAssociate(Long id){
        return portOut.findById(id)
                .orElseThrow(() -> new RuntimeException("Associate not found!"));
    }


}
