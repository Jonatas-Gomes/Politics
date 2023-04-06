package br.com.compass.party.domain.dto;

import br.com.compass.party.domain.enums.Ideology;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyDTO {
    @NotNull(message = "this field cannot be null")
    @NotEmpty(message = "this field cannot be empty")
    private String partyName;
    @NotNull(message = "this field cannot be null")
    @NotEmpty(message = "this field cannot be empty")
    private String acronym;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "must be Right, Center or Left")
    private Ideology ideology;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate foundationDate;
}
