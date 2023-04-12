package br.com.compass.associate.domain.dto;

import br.com.compass.associate.domain.enums.PoliticalOffice;
import br.com.compass.associate.domain.enums.Sex;
import br.com.compass.associate.framework.helper.config.LocalDateDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^([a-zA-ZãÃéÉíÍóÓêÊôÔáÁúÚç\s])+$", message = "must be only letters!")
    private String fullName;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "must be any of: Alderman | Mayor | State Deputy | Federal Deputy | Senator | Governor | President | None")
    private PoliticalOffice politicalOffice;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @NotNull
    private LocalDate birthday;
    @NotNull(message = "must be Male or Female")
    @Enumerated(EnumType.STRING)
    private Sex sex;

}
