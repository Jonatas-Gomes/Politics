package br.com.compass.party.framework.adapters.in.rest;

import br.com.compass.party.application.ports.in.PartyUseCase;
import br.com.compass.party.domain.dto.AssociateResponse;
import br.com.compass.party.domain.dto.PageableResponse;
import br.com.compass.party.domain.dto.PartyDTO;
import br.com.compass.party.domain.dto.PartyResponse;
import br.com.compass.party.domain.enums.Ideology;
import br.com.compass.party.domain.model.Associate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parties")
public class PartyControler {

    private final PartyUseCase partyUseCase;
    @PostMapping
    public ResponseEntity<PartyResponse> createParty(@RequestBody PartyDTO partyDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(partyUseCase.createParty(partyDTO));
    }
    @GetMapping
    public ResponseEntity<PageableResponse> findAll(@RequestParam(required = false)Ideology ideology, Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(partyUseCase.findAll(ideology, pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<PartyResponse> findById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(partyUseCase.findById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id){
        partyUseCase.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<PartyResponse> update(@PathVariable String id, @RequestBody PartyDTO partyDTO){
        return ResponseEntity.status(HttpStatus.OK).body(partyUseCase.update(id, partyDTO));
    }

    @PostMapping("/associates/{idParty}")
    public ResponseEntity<PartyResponse>bindAssociation(@RequestBody Associate associate, @PathVariable String idParty){
        return ResponseEntity.status(HttpStatus.OK).body(partyUseCase.bindAssociation(associate, idParty));
    }
    @GetMapping("/{id}/associates")
    public ResponseEntity<List<AssociateResponse>> getAffiliates(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(partyUseCase.getAffiliates(id));
    }

}
