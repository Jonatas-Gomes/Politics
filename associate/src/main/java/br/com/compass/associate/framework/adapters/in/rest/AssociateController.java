package br.com.compass.associate.framework.adapters.in.rest;

import br.com.compass.associate.application.ports.in.AssociateUseCase;
import br.com.compass.associate.domain.dto.*;
import br.com.compass.associate.domain.enums.PoliticalOffice;
import br.com.compass.associate.framework.adapters.out.partyClient.PartyClient;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/associates")
public class AssociateController {
    private final AssociateUseCase useCase;

    private final PartyClient partyClient;
    @PostMapping
    public ResponseEntity<AssociateResponse> createAssociate(@RequestBody AssociateDTO associateDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.createAssociate(associateDTO));
    }
    @GetMapping
    public ResponseEntity<PageableResponse>findAll(@RequestParam(required = false) PoliticalOffice politicalOffice, Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(useCase.findAll(politicalOffice, pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<AssociateResponse> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(useCase.findById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<AssociateResponse> update(@PathVariable Long id, @RequestBody AssociateDTO associateDTO){
        return ResponseEntity.status(HttpStatus.OK).body(useCase.update(id, associateDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        useCase.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping("/parties")
    public ResponseEntity<PageablePartyResponse>getParties(){
        return ResponseEntity.status(HttpStatus.OK).body(partyClient.findAll());
    }
}
