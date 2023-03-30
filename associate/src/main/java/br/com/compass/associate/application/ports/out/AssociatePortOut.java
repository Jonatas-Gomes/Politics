package br.com.compass.associate.application.ports.out;

import br.com.compass.associate.domain.model.Associate;

public interface AssociatePortOut {
    Associate save(Associate associate);
}
