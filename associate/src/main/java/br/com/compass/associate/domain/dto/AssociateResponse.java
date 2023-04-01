package br.com.compass.associate.domain.dto;

import br.com.compass.associate.domain.enums.PoliticalOffice;
import br.com.compass.associate.domain.enums.Sex;
import br.com.compass.associate.domain.model.Party;
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
    private LocalDate birthday;
    private Sex sex;
    private Party party;
}
