package org.example.royaleanalytics.exception;

public class UsernameUsedException extends ConflictServiceException {
    public UsernameUsedException(String message) {
        super("User with username %s already exists.");
    }
}
