package com.cookMaster.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TechnicalException extends RuntimeException {
    public TechnicalException(String message) {
        super(message);
    }

    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }
}
