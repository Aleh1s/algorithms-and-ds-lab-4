package org.example.exception;

public class GraphFactoryException extends Exception {
    public GraphFactoryException() {
    }

    public GraphFactoryException(String message) {
        super(message);
    }

    public GraphFactoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public GraphFactoryException(Throwable cause) {
        super(cause);
    }
}
