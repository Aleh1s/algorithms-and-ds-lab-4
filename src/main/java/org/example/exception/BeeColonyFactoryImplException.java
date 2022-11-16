package org.example.exception;

public class BeeColonyFactoryImplException extends BeeColonyFactoryException {
    public BeeColonyFactoryImplException() {
    }

    public BeeColonyFactoryImplException(String message) {
        super(message);
    }

    public BeeColonyFactoryImplException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeeColonyFactoryImplException(Throwable cause) {
        super(cause);
    }
}
