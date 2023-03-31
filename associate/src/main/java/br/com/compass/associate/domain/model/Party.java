package br.com.compass.associate.domain.model;

import br.com.compass.associate.domain.enums.Ideology;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class Party {
    @Id
    private String idParty;
    private String partyName;
    private String acronym;

    @Enumerated(EnumType.STRING)
    private Ideology ideology;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate foundationDate;
}
