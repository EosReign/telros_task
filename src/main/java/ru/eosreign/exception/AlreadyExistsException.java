package ru.eosreign.exception;

/**
 * gRPC-Status: 6
 * Http-Status: 409
 * link: <a href="https://developer.mozilla.org/ru/docs/Web/HTTP/Reference/Status/409">...</a>
 */
public class AlreadyExistsException extends RuntimeException {

    public static final String F_USER_EMAIL_ALREADY_EXIST = "Пользователь с почтой: %s - уже существует.";
    public static final String F_USER_PHONE_ALREADY_EXIST = "Пользователь с номером телефона: %s - уже существует.";

    public AlreadyExistsException() {
    }

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistsException(Throwable cause) {
        super(cause);
    }
}