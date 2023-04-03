package br.com.compass.party.application.ports.in;

import br.com.compass.party.domain.dto.AssociateResponse;
import br.com.compass.party.domain.dto.PageableResponse;
import br.com.compass.party.domain.dto.PartyDTO;
import br.com.compass.party.domain.dto.PartyResponse;
import br.com.compass.party.domain.enums.Ideology;
import br.com.compass.party.domain.model.Associate;
import br.com.compass.party.domain.model.Party;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PartyUseCase {
    PartyResponse createParty(PartyDTO partyDTO);
    PageableResponse findAll(Ideology ideology, Pageable pageable);

    void delete(String id);
    public PartyResponse findById(String id);

    public PartyResponse update(String id, PartyDTO partyDTO);

    public PartyResponse bindAssociation(Associate associate, String idParty);

    public void deleteAssociation(String idParty, Long idAssociate);

    public List<AssociateResponse> getAffiliates(String id);

}
