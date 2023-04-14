package br.com.compass.party.application.ports.out;

import br.com.compass.party.domain.enums.Ideology;
import br.com.compass.party.domain.model.Party;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PartyPortOut {
    <S extends Party> S save(S entity);
    Page<Party> findAll(Pageable pageable);

    Page<Party> findByIdeology(Ideology ideology, Pageable pageable);

    Optional<Party> findById(String id);

    void deleteById(String id);


}
