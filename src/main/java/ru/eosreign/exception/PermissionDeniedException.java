package ru.eosreign.exception;

/**
 * gRPC-Status: 7
 * Http-Status: 403
 * link: <a href="https://developer.mozilla.org/ru/docs/Web/HTTP/Reference/Status/403">...</a>
 */
public class PermissionDeniedException extends RuntimeException {

    public static final String ADMIN_ROLE_REASON = "Отказ в доступе. Пользователь не является админом.";
    public static final String TOKEN_EXPIRED_REASON = "Жизненный цикл токена закончился. Пройдите логин снова.";

    public PermissionDeniedException() {
    }

    public PermissionDeniedException(String message) {
        super(message);
    }

    public PermissionDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PermissionDeniedException(Throwable cause) {
        super(cause);
    }
}