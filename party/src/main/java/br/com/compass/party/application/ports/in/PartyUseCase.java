package br.com.compass.party.application.ports.in;

import br.com.compass.party.domain.dto.PageableResponse;
import br.com.compass.party.domain.dto.PartyDTO;
import br.com.compass.party.domain.dto.PartyResponse;
import br.com.compass.party.domain.enums.Ideology;
import org.springframework.data.domain.Pageable;

public interface PartyUseCase {
    PartyResponse createParty(PartyDTO partyDTO);
    PageableResponse findAll(Ideology ideology, Pageable pageable);

    void delete(String id);
    public PartyResponse findById(String id);

    public PartyResponse update(String id, PartyDTO partyDTO);


}