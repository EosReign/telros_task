package ru.eosreign.client;

import io.minio.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.eosreign.exception.InternalException;

import java.io.IOException;
import java.io.InputStream;

import static ru.eosreign.exception.InternalException.*;

@Slf4j
@Component
@AllArgsConstructor
public class MinioCommonClient {

    private final MinioClient minioClient;

    /**
     * param: objectName = имя объекта
     * param: bucketName = корзина объекта
     * param: inputStream = стрим байт объекта
     * param: contentType = type объекта
     */
    public void putObject(String objectName,
                          String bucketName,
                          InputStream inputStream,
                          String contentType) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .contentType(contentType)
                    .stream(inputStream, -1, 10485760)
                    .build());
        } catch (Exception e) {
            throw new InternalException(F_EXPORT_FILE_ERROR + e.getMessage());
        } finally {
            closeInputStream(inputStream);
        }
    }

    /**
     * param: imageName = имя изображения в формате "Id.ContentTypeExtension"
     */
    public String getObject(String imageName, String bucketName) {
        try (InputStream stream = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(imageName)
                .build())) {
            return new String(stream.readAllBytes());
        } catch (Exception e) {
            throw new InternalException(F_IMPORT_FILE_ERROR + e.getMessage());
        }
    }

    public void removeObject(String objectName, String bucketName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
        } catch (Exception e) {
            throw new InternalException(F_DELETE_FILE_ERROR + e.getMessage());
        }
    }

    private void closeInputStream(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.warn(e.getMessage());
            }
        }
    }
}