package br.com.compass.associate.framework.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RequiredArgsConstructor
@RestControllerAdvice
public class AssociateExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(RequestException.class)
    public ResponseEntity<ErrorMessage> handleRequestException(RequestException exception){
        var response = ErrorMessage.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(exception.getStatus()).body(response);
    }
}
