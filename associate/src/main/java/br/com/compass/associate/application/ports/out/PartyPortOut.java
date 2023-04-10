package br.com.compass.associate.application.ports.out;

import br.com.compass.associate.domain.model.Party;

public interface PartyPortOut {
    Party save(Party party);
}
