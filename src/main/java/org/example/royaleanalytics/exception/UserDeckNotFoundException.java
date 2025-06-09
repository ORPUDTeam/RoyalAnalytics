package org.example.royaleanalytics.exception;

import org.springframework.http.HttpStatus;

public class UserDeckNotFoundException extends ServiceException {
    public UserDeckNotFoundException(int deckId) {
        super(HttpStatus.NOT_FOUND,"не нашел деку с id %d".formatted(deckId));
    }
}
