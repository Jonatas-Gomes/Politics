package br.com.compass.associate.framework.adapters.out.repository.database;

import br.com.compass.associate.domain.enums.PoliticalOffice;
import br.com.compass.associate.domain.model.Associate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface AssociateJpaRepository extends JpaRepository<Associate,Long > {

    Page<Associate> findByPoliticalOffice(PoliticalOffice politicalOffice, Pageable pageable);

    void deleteById(Long id);
}
