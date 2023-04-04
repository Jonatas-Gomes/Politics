package br.com.compass.associate.framework.exception;

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
