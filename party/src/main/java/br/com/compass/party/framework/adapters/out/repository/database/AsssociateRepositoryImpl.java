package br.com.compass.party.framework.adapters.out.repository.database;

import br.com.compass.party.application.ports.out.AssociatePortOut;
import br.com.compass.party.domain.model.Associate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AsssociateRepositoryImpl implements AssociatePortOut {

    private final AssociateJpaRepository repository;
    @Override
    public Associate save(Associate associate) {
        return repository.save(associate);
    }
}
