package br.com.compass.associate.application.ports.in;
import br.com.compass.associate.domain.dto.AssociateDTO;
import br.com.compass.associate.domain.dto.AssociateResponse;
import br.com.compass.associate.domain.dto.AssociationDTO;
import br.com.compass.associate.domain.dto.PageableResponse;
import br.com.compass.associate.domain.enums.PoliticalOffice;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Pageable;

public interface AssociateUseCase {
    public AssociateResponse createAssociate(AssociateDTO associateDTO);

    public PageableResponse findAll(PoliticalOffice politicalOffice, Pageable pageable);

    public AssociateResponse findById(Long id);

    public void delete(Long id);

    public AssociateResponse update(Long id, AssociateDTO associateDTO);
    public AssociateResponse bindAssociate(AssociationDTO associationDTO);
    public AssociateResponse removeAssociation(Long idAssociate, String idParty) throws JsonProcessingException;

}
