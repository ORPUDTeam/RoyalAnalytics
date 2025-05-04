package org.example.royaleanalytics.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ServiceException extends RuntimeException {
    private HttpStatus httpStatus;

    public ServiceException(HttpStatus httpStatus, String message) {}
}
