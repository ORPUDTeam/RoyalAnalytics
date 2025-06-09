package org.example.royaleanalytics.exception;

import org.springframework.http.HttpStatus;

public class CardNotFoundException extends ServiceException {
    public CardNotFoundException(int id) {
        super(HttpStatus.NOT_FOUND,"не найдена карта с id %d".formatted(id));
    }
}
