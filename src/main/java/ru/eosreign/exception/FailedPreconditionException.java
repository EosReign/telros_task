package ru.eosreign.exception;

/**
 * gRPC-Status: 9
 * Http-Status: 400
 * link: <a href="https://developer.mozilla.org/ru/docs/Web/HTTP/Reference/Status/400">...</a>
 */
public class FailedPreconditionException extends RuntimeException {

    public static final String INEQUALITY_PASSWORD_AND_REPEAT_PASSWORD = "Ошибка: пароль и повторный пароль должны совпадать.";

    public FailedPreconditionException() {
    }

    public FailedPreconditionException(String message) {
        super(message);
    }

    public FailedPreconditionException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailedPreconditionException(Throwable cause) {
        super(cause);
    }
}