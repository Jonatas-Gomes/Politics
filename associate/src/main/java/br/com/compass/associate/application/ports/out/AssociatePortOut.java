package br.com.compass.associate.application.ports.out;

import br.com.compass.associate.domain.enums.PoliticalOffice;
import br.com.compass.associate.domain.model.Associate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AssociatePortOut {
    Associate save(Associate associate);

    Page<Associate> findAll(Pageable pageable);

    Page<Associate> findByPoliticalOffice(PoliticalOffice politicalOffice, Pageable pageable);

    Optional<Associate> findById(Long id);

    void deleteById(Long id);
}
