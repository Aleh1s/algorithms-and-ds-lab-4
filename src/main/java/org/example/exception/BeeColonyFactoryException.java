package org.example.exception;

public class BeeColonyFactoryException extends Exception {
    public BeeColonyFactoryException() {
    }

    public BeeColonyFactoryException(String message) {
        super(message);
    }

    public BeeColonyFactoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeeColonyFactoryException(Throwable cause) {
        super(cause);
    }
}
