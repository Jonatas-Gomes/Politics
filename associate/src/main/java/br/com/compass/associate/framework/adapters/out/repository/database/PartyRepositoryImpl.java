package br.com.compass.associate.framework.adapters.out.repository.database;

import br.com.compass.associate.application.ports.out.PartyPortOut;
import br.com.compass.associate.domain.model.Party;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PartyRepositoryImpl implements PartyPortOut {
    private final PartyJpaRepository repository;
    @Override
    public Party save(Party party) {
        return repository.save(party);
    }
}
