package br.com.compass.associate.framework.adapters.out.partyClient;

import br.com.compass.associate.domain.dto.PartyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("ms-party")
public interface PartyClient {
    @RequestMapping("/parties")
    @GetMapping
    PartyDTO findAll();
}
