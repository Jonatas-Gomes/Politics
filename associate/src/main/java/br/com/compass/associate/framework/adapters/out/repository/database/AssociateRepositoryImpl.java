package br.com.compass.associate.framework.adapters.out.repository.database;

import br.com.compass.associate.application.ports.out.AssociatePortOut;
import br.com.compass.associate.domain.enums.PoliticalOffice;
import br.com.compass.associate.domain.model.Associate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class AssociateRepositoryImpl implements AssociatePortOut {

    private final AssociateJpaRepository repository;
    @Override
    public Associate save(Associate associate) {
        return repository.save(associate);
    }

    @Override
    public Page<Associate> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Associate> findByPoliticalOffice(PoliticalOffice politicalOffice, Pageable pageable) {
        return repository.findByPoliticalOffice(politicalOffice, pageable);
    }

    @Override
    public Optional<Associate> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
