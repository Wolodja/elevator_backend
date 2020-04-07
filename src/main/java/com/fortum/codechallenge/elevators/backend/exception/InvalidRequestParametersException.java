package com.fortum.codechallenge.elevators.backend.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidRequestParametersException extends RuntimeException {

    public InvalidRequestParametersException(String message) {
        super(message);
    }
}