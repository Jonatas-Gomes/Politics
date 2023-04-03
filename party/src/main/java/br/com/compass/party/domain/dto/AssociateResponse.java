package br.com.compass.party.domain.dto;

import br.com.compass.party.domain.enums.PoliticalOffice;
import br.com.compass.party.domain.enums.Sex;
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
public class AssociateResponse {
    private Long id;
    private String fullName;
    private PoliticalOffice politicalOffice;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthday;
    private Sex sex;
}
