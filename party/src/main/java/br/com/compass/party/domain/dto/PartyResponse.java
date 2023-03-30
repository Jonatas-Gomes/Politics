package br.com.compass.party.domain.dto;

import br.com.compass.party.domain.enums.Ideology;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyResponse {
    private String idParty;
    private String partyName;
    private String acronym;
    private Ideology ideology;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate foundationDate;

}

