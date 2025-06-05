package org.example.royaleanalytics.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class RegistrationException extends RuntimeException {
    private final Map<String, String> fieldErrors;

    public RegistrationException(Map<String, String> fieldErrors) {
      super("Validation failed");
      this.fieldErrors = fieldErrors;
    }
}
