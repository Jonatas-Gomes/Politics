package br.com.compass.associate.application.service.utils;

import br.com.compass.associate.domain.dto.AssociateDTO;
import br.com.compass.associate.domain.dto.AssociateResponse;
import br.com.compass.associate.domain.dto.AssociationDTO;
import br.com.compass.associate.domain.model.Associate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MapperUtils {
    private final ModelMapper mapper;
    public AssociateResponse mapAssociateToAssociateResponse(Associate associate){
        return mapper.map(associate, AssociateResponse.class);
    }

    public Associate mapAssociateDtoToAssociate(AssociateDTO associateDTO) {
        return  mapper.map(associateDTO, Associate.class);
    }

    public Associate associateUpdateMapping(Associate associate, AssociateDTO associateDTO){
        associate.setFullName(associateDTO.getFullName());
        associate.setSex(associateDTO.getSex());
        associate.setBirthday(associateDTO.getBirthday());
        associate.setPoliticalOffice(associateDTO.getPoliticalOffice());

        return associate;
    }

    public AssociationDTO associationDTOBuilder(Long idAssociate, String idParty){
        return AssociationDTO.builder()
                .idAssociate(idAssociate)
                .idParty(idParty)
                .build();
    }
}
