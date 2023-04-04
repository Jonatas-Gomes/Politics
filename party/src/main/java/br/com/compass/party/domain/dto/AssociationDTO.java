package br.com.compass.party.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssociationDTO {
    private String idParty;
    private Long idAssociate;
}
