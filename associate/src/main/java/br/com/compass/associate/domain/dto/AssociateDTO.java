package br.com.compass.associate.domain.dto;

import br.com.compass.associate.domain.enums.PoliticalOffice;
import br.com.compass.associate.domain.enums.Sex;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssociateDTO {
    private String fullName;
    private PoliticalOffice politicalOffice;
    private LocalDate birthday;
    private Sex sex;

}
