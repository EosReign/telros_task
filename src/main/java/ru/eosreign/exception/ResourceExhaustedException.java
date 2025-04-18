package ru.eosreign.exception;

/**
 * gRPC-Status: 8
 * Http-Status: 429
 * link: <a href="https://developer.mozilla.org/ru/docs/Web/HTTP/Reference/Status/429">...</a>
 */
public class ResourceExhaustedException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "Подождите минуту, прежде чем запросить повторную отправку.";

    public ResourceExhaustedException() {
    }

    public ResourceExhaustedException(String message) {
        super(message);
    }

    public ResourceExhaustedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceExhaustedException(Throwable cause) {
        super(cause);
    }
}