package br.com.compass.associate.domain.dto;

import br.com.compass.associate.domain.model.Associate;
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
    private List<Associate> associates;
}
