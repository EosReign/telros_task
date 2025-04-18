package ru.eosreign.exception;

/**
 * gRPC-Status: 3
 * Http-Status: 400
 * link: <a href="https://developer.mozilla.org/ru/docs/Web/HTTP/Reference/Status/400">...</a>
 */
public class InvalidArgumentException extends RuntimeException {

    public static final String INVALID_OLD_PASSWORD = "Введен неправильный старый пароль.";
    public static final String INVALID_IMAGE_CONTENT_TYPE = "Неподдерживаемый формат изображения. Убедитесь, что изображение соответствует формату \".jpg\", \".jpeg\" или \".png\"";


    public InvalidArgumentException() {
    }

    public InvalidArgumentException(String message) {
        super(message);
    }

    public InvalidArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidArgumentException(Throwable cause) {
        super(cause);
    }
}