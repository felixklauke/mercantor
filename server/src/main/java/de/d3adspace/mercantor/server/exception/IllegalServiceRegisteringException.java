package de.d3adspace.mercantor.server.exception;

/**
 * An exception that will be thrown when a user tries to register an invalid service.
 *
 * @author Felix Klauke <fklauke@itemis.de>
 */
public class IllegalServiceRegisteringException extends Exception {

    public IllegalServiceRegisteringException() {
    }

    public IllegalServiceRegisteringException(String message) {
        super(message);
    }

    public IllegalServiceRegisteringException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalServiceRegisteringException(Throwable cause) {
        super(cause);
    }

    public IllegalServiceRegisteringException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
