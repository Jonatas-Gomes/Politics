package br.com.compass.party.application.ports.in;

import br.com.compass.party.domain.dto.*;
import br.com.compass.party.domain.enums.Ideology;
import br.com.compass.party.domain.model.Associate;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PartyUseCase {
    PartyResponse createParty(PartyDTO partyDTO);
    PageableResponse findAll(Ideology ideology, Pageable pageable);

    void delete(String id);
    public PartyResponse findById(String id);

    public PartyResponse update(String id, PartyDTO partyDTO) throws JsonProcessingException;

    public PartyResponse bindAssociation(Associate associate, String idParty);

    public void deleteAssociation(AssociationDTO associationDTO);

    public List<AssociateResponse> getAffiliates(String id);

    public void updateAssociate(Associate associate);
}
