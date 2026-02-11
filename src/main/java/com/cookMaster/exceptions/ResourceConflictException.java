package com.cookMaster.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResourceConflictException extends RuntimeException{
    public ResourceConflictException(String message) {
        super(message);
    }

    public ResourceConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
