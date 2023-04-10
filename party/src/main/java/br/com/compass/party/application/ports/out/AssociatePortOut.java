package br.com.compass.party.application.ports.out;

import br.com.compass.party.domain.model.Associate;

public interface AssociatePortOut {
    Associate save(Associate associate);
}
