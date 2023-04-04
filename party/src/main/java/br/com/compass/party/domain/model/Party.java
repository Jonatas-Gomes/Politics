package br.com.compass.party.domain.model;

import br.com.compass.party.domain.enums.Ideology;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Associate> associates;

}
