package br.com.compass.associate.framework.adapters.out.repository.database;

import br.com.compass.associate.domain.model.Associate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociateJpaRepository extends JpaRepository<Associate,Long > {

}
