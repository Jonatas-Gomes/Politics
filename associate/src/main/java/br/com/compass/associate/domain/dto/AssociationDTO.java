package br.com.compass.associate.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssociationDTO {
    @NotNull
    @Positive(message = "must be positive number")
    private Long idAssociate;
    @NotNull
    @NotEmpty
    private String idParty;
}
