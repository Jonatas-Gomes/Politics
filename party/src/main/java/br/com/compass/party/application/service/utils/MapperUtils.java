package br.com.compass.party.application.service.utils;

import br.com.compass.party.domain.dto.AssociateResponse;
import br.com.compass.party.domain.dto.PartyDTO;
import br.com.compass.party.domain.dto.PartyResponse;
import br.com.compass.party.domain.model.Associate;
import br.com.compass.party.domain.model.Party;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MapperUtils {

    private final ModelMapper mapper;
    public Party dtoToPartyModel(PartyDTO partyDTO){
        return mapper.map(partyDTO, Party.class);
    }

    public PartyResponse modelToPartyResponse(Party party){
        return mapper.map(party, PartyResponse.class);
    }

    public Party updatePartyMapping(Party party, PartyDTO partyDTO){
        party.setPartyName(partyDTO.getPartyName());
        party.setAcronym(partyDTO.getAcronym());
        party.setIdeology(partyDTO.getIdeology());
        party.setFoundationDate(partyDTO.getFoundationDate());

        return party;
    }

    public List<AssociateResponse>listAssociatestoListResponse(List<Associate> associates){
        return mapper.map(associates,new TypeToken<List<AssociateResponse>>(){}.getType());
    }
}
