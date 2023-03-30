package br.com.compass.party.domain.dto;

import br.com.compass.party.domain.enums.Ideology;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyDTO {
    private String partyName;
    private String acronym;
    @Enumerated(EnumType.STRING)
    @NonNull
    private Ideology ideology;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate foundationDate;
}
