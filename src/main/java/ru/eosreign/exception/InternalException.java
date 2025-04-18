package ru.eosreign.exception;

/**
 * gRPC-Status: 13
 * Http-Status: 500
 * link: <a href="https://developer.mozilla.org/ru/docs/Web/HTTP/Reference/Status/500">...</a>
 */
public class InternalException extends RuntimeException {

    public static final String READING_ERROR = "Ошибка при чтении файла";
    public static final String F_EXPORT_FILE_ERROR = "Ошибка при загрузке файла: ";
    public static final String F_IMPORT_FILE_ERROR = "Ошибка при получении файла: ";
    public static final String F_DELETE_FILE_ERROR = "Ошибка при удалении файла: ";

    public InternalException() {
    }

    public InternalException(String message) {
        super(message);
    }

    public InternalException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalException(Throwable cause) {
        super(cause);
    }
}