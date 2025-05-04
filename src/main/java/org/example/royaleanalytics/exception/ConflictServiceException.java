package org.example.royaleanalytics.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ConflictServiceException extends ServiceException {
    public ConflictServiceException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
