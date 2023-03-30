package br.com.compass.associate.application.ports.in;

import br.com.compass.associate.domain.dto.AssociateDTO;
import br.com.compass.associate.domain.dto.AssociateResponse;
import br.com.compass.associate.domain.model.Associate;

public interface AssociateUseCase {
    public AssociateResponse createAssociate(AssociateDTO associateDTO);
}
