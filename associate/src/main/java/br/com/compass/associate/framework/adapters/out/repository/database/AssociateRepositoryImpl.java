package br.com.compass.associate.framework.adapters.out.repository.database;

import br.com.compass.associate.application.ports.out.AssociatePortOut;
import br.com.compass.associate.domain.model.Associate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AssociateRepositoryImpl implements AssociatePortOut {

    private final AssociateJpaRepository repository;
    @Override
    public Associate save(Associate associate) {
        return repository.save(associate);
    }

}
