package br.com.compass.associate.framework.adapters.out.partyClient;

import br.com.compass.associate.domain.dto.PageablePartyResponse;
import br.com.compass.associate.domain.model.Associate;
import br.com.compass.associate.domain.model.Party;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient("ms-party")
public interface PartyClient {

    @GetMapping("/parties")
    PageablePartyResponse findAll();

    @PostMapping("/parties/associates/{idParty}")
    Party bindAssociation(@RequestBody Associate associate, @PathVariable String idParty);

}
