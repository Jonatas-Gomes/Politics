package br.com.compass.associate.application.service;

import br.com.compass.associate.application.ports.in.AssociateUseCase;
import br.com.compass.associate.application.ports.out.AssociatePortOut;
import br.com.compass.associate.domain.dto.AssociateDTO;
import br.com.compass.associate.domain.dto.AssociateResponse;
import br.com.compass.associate.domain.model.Associate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssociateService implements AssociateUseCase{

    private final ModelMapper mapper;
    private final AssociatePortOut portOut;
    @Override
    public AssociateResponse createAssociate(AssociateDTO associateDTO) {
        Associate associate = mapper.map(associateDTO, Associate.class);
        portOut.save(associate);
        return mapper.map(associate, AssociateResponse.class);
    }
}
