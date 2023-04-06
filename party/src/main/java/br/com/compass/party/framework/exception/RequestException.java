package br.com.compass.party.framework.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class RequestException extends RuntimeException{
    private String message;
    private HttpStatus status;
}
