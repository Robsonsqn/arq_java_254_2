package br.edu.infnet.robsongallinaapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidGameDataException extends RuntimeException {
    public InvalidGameDataException(String message) {
        super(message);
    }
}