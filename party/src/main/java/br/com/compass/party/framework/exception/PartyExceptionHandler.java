package br.com.compass.party.framework.exception;

import jakarta.validation.UnexpectedTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestControllerAdvice
public class PartyExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;
    @ExceptionHandler(RequestException.class)
    public ResponseEntity<ErrorMessage> handleRequestException(RequestException exception){
        var response = ErrorMessage.builder()
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(exception.getStatus()).body(response);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ExceptionValidResponse> errors = new ArrayList<>();
        List<FieldError>fieldErrors = ex.getBindingResult().getFieldErrors();

        fieldErrors.forEach(error ->{
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            var errorResponse = new ExceptionValidResponse(error.getField(), message);

            errors.add(errorResponse);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object>handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
        Class<?> type = ex.getRequiredType();
        ErrorMessage response;

        if(type.isEnum()){
            response = ErrorMessage.builder().message("No ideology found, must be Right, Center or Left").build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        System.out.println(ex.getPropertyName());
        response = ErrorMessage.builder().message(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<Object>handleUnexpectedTypeException(UnexpectedTypeException exception){
        var response = ErrorMessage.builder().message("unexpected field type, check the filled fields").build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
