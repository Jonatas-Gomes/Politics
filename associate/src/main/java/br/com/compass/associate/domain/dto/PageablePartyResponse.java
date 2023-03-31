package br.com.compass.associate.domain.dto;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageablePartyResponse {
    private Integer numberOfElements;
    private Long totalElements;
    private Integer totalPages;
    private List<PartyDTO> parties;
}
