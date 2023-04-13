package br.com.compass.party.framework.config;

import br.com.compass.party.framework.exception.RequestException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

@Configuration
public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String value = jsonParser.getText();
        var formatter = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .appendOptional(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
                .appendOptional(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

                .toFormatter();

        try {
            return LocalDate.parse(value, formatter);
        } catch (DateTimeParseException e) {
            throw new RequestException("Invalid date, must be in format yyyy-MM-dd", HttpStatus.BAD_REQUEST);
        }
    }

}