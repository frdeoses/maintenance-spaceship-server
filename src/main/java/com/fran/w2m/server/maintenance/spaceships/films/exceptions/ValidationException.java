package com.fran.w2m.server.maintenance.spaceships.films.exceptions;

import java.util.Map;
import java.util.StringJoiner;

public class ValidationException extends Exception {

    private final Map<String, String> errors;

    public ValidationException(Map<String, String> errors) {
        super(buildMessage(errors));
        this.errors = errors;
    }

    private static String buildMessage(Map<String, String> errors) {
        StringJoiner messageJoiner = new StringJoiner(", ", "Errores de validación: ", ".");
        errors.forEach((field, error) -> messageJoiner.add(field + ": " + error));
        return messageJoiner.toString();
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
