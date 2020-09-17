package org.neverplan2.wololo.exception;

public class WololoException extends Exception {

    public WololoException(String message) {
        super(message);
    }

    public WololoException(String message, Throwable cause) {
        super(message, cause);
    }
}
