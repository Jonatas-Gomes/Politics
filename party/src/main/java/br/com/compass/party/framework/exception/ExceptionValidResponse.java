package br.com.compass.party.framework.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionValidResponse {
    private String field;
    private String error;
}
