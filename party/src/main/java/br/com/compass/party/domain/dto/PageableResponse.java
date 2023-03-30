package br.com.compass.party.domain.dto;

import br.com.compass.party.domain.model.Party;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageableResponse {
    private Integer numberOfElements;
    private Long totalElements;
    private Integer totalPages;
    private List<Party> parties;
}
