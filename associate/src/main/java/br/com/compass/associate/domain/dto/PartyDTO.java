package br.com.compass.associate.domain.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PartyDTO {
    private String idParty;
    private String partyName;
    private String acronym;
}
