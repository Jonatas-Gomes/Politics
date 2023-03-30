package br.com.compass.associate.framework.adapters.in.rest;

import br.com.compass.associate.application.ports.in.AssociateUseCase;
import br.com.compass.associate.domain.dto.AssociateDTO;
import br.com.compass.associate.domain.dto.AssociateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/associates")
public class AssociateController {
    private final AssociateUseCase useCase;
    @PostMapping
    public ResponseEntity<AssociateResponse> createAssociate(@RequestBody AssociateDTO associateDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.createAssociate(associateDTO));
    }
}
