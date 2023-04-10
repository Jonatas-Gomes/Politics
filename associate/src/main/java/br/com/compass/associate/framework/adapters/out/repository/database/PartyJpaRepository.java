package br.com.compass.associate.framework.adapters.out.repository.database;

import br.com.compass.associate.domain.model.Party;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyJpaRepository extends JpaRepository<Party,String> {

}
