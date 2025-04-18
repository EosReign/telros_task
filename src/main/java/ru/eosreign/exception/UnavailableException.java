package ru.eosreign.exception;

/**
 * gRPC-Status: 14
 * Http-Status: 503
 * link: <a href="https://developer.mozilla.org/ru/docs/Web/HTTP/Reference/Status/503">...</a>
 */
public class UnavailableException extends RuntimeException {
    public UnavailableException() {
    }

    public UnavailableException(String message) {
        super(message);
    }

    public UnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnavailableException(Throwable cause) {
        super(cause);
    }
}