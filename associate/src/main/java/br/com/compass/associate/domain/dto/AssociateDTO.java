package br.com.compass.associate.domain.dto;

import br.com.compass.associate.domain.enums.PoliticalOffice;
import br.com.compass.associate.domain.enums.Sex;
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
public class AssociateDTO {
    @NotNull(message = "this field cannot be null ")
    @NotEmpty(message = "this field cannot be empty")
    private String fullName;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "must be any of: Alderman | Mayor | State Deputy | Federal Deputy | Senator | Governor | President | None")
    private PoliticalOffice politicalOffice;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate birthday;
    @NotNull(message = "must be Male or Female")
    @Enumerated(EnumType.STRING)
    private Sex sex;

}
