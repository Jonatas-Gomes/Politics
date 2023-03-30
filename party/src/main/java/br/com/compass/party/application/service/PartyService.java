package br.com.compass.party.application.service;

import br.com.compass.party.application.ports.in.PartyUseCase;
import br.com.compass.party.application.ports.out.PartyPortOut;
import br.com.compass.party.domain.dto.PageableResponse;
import br.com.compass.party.domain.dto.PartyDTO;
import br.com.compass.party.domain.dto.PartyResponse;
import br.com.compass.party.domain.enums.Ideology;
import br.com.compass.party.domain.model.Party;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class PartyService implements PartyUseCase {

    private final ModelMapper mapper;
    private final PartyPortOut portOut;
    @Override
    public PartyResponse createParty(PartyDTO partyDTO) {
        Party party = mapper.map(partyDTO, Party.class);
        party.setIdParty(generateID());
        portOut.save(party);
        return mapper.map(party, PartyResponse.class);
    }

    @Override
    public PageableResponse findAll(Ideology ideology, Pageable pageable){
        Page<Party> page = ideology == null ?
                portOut.findAll(pageable) :
                portOut.findByIdeology(ideology, pageable);
        if(page.isEmpty()){
            throw new RuntimeException("Parties with this ideology not found!");
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
        return mapper.map(getParty(id), PartyResponse.class);
    }

    @Override
    public PartyResponse update(String id, PartyDTO partyDTO) {
        var party = getParty(id);

        party.setPartyName(partyDTO.getPartyName());
        party.setAcronym(partyDTO.getAcronym());
        party.setIdeology(partyDTO.getIdeology());
        party.setFoundationDate(partyDTO.getFoundationDate());

        portOut.save(party);

        return mapper.map(party, PartyResponse.class);
    }

    @Override
    public void delete(String id) {
        getParty(id);
        portOut.deleteById(id);
    }

    private Party getParty(String id){
        return portOut.findById(id)
                .orElseThrow(()-> new RuntimeException("Party with this id not found"));
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
