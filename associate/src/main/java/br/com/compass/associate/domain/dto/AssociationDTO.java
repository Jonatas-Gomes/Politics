package br.com.compass.associate.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssociationDTO {
    @NotNull
    @NotEmpty
    private Long idAssociate;
    @NotNull
    @NotEmpty
    private String idParty;
}
