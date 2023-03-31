package br.com.compass.associate.framework.adapters.out.partyClient;

import br.com.compass.associate.domain.dto.PageablePartyResponse;
import br.com.compass.associate.domain.model.Associate;
import br.com.compass.associate.domain.model.Party;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient("ms-party")
public interface PartyClient {

    @GetMapping("/parties")
    PageablePartyResponse findAll();
    @GetMapping("/parties/{id}")
    Party findById(@PathVariable String id);

    @PostMapping("/parties/{id}")
    Party bindAssociation(@RequestParam Associate associate, String idParty);

}
