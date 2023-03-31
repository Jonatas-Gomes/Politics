package br.com.compass.party.domain.model;

import br.com.compass.party.domain.enums.PoliticalOffice;
import br.com.compass.party.domain.enums.Sex;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Associate {
    private Long id;
    private String fullName;
    @Enumerated(EnumType.STRING)
    private PoliticalOffice politicalOffice;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthday;
    @Enumerated(EnumType.STRING)
    private Sex sex;
}
