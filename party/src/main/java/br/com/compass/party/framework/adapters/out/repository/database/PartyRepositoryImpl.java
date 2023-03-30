package br.com.compass.party.framework.adapters.out.repository.database;

import br.com.compass.party.application.ports.out.PartyPortOut;
import br.com.compass.party.domain.enums.Ideology;
import br.com.compass.party.domain.model.Party;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@RequiredArgsConstructor
@Repository
public class PartyRepositoryImpl implements PartyPortOut {

    private final PartyJpaRepository repository;
    @Override
    public <S extends Party> S save(S party) {
        return repository.save(party);
    }

    @Override
    public Page<Party> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Party> findByIdeology(Ideology ideology, Pageable pageable) {
       return repository.findByIdeology(ideology, pageable);
    }

    @Override
    public Optional<Party> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
