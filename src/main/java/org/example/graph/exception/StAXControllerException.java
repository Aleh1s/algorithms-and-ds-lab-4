package org.example.graph.exception;

import org.xml.sax.SAXException;

public class StAXControllerException extends Exception {
    public StAXControllerException() {
    }

    public StAXControllerException(String message) {
        super(message);
    }

    public StAXControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public StAXControllerException(Throwable cause) {
        super(cause);
    }
}
