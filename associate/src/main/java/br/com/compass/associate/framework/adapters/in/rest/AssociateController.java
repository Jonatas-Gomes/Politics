package br.com.compass.associate.framework.adapters.in.rest;

import br.com.compass.associate.application.ports.in.AssociateUseCase;
import br.com.compass.associate.domain.dto.AssociateDTO;
import br.com.compass.associate.domain.dto.AssociateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/associates")
public class AssociateController {
    private final AssociateUseCase useCase;
    @GetMapping
    public ResponseEntity<AssociateResponse> createAssociate(@RequestBody AssociateDTO associateDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.createAssociate(associateDTO));
    }
}
