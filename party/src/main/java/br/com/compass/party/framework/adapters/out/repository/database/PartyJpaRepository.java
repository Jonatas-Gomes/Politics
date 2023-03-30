package br.com.compass.party.framework.adapters.out.repository.database;

import br.com.compass.party.domain.enums.Ideology;
import br.com.compass.party.domain.model.Party;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyJpaRepository extends JpaRepository<Party, String> {

    Page<Party>findByIdeology(Ideology ideology, Pageable pageable);

    void deleteById(String id);
}
