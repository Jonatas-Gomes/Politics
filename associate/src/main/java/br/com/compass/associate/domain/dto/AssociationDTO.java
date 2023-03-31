package br.com.compass.associate.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssociationDTO {
    private Long idAssociate;
    private String idParty;
}
