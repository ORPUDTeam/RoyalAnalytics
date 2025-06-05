package org.example.royaleanalytics.exception;

import org.springframework.http.HttpStatus;

public class NotFoundServiceException extends ServiceException {
    public NotFoundServiceException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
