package ru.eosreign.exception;

/**
 * gRPC-Status: 5
 * Http-Status: 404
 * link: <a href="https://developer.mozilla.org/ru/docs/Web/HTTP/Reference/Status/404">...</a>
 */
public class NotFoundException extends RuntimeException {

    public static final String F_USER_EMAIL_MESSAGE = "Пользователь с email: %s не существует.";
    public static final String F_USER_PHONE_MESSAGE = "Пользователь с телефоном: %s - не существует.";

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
}