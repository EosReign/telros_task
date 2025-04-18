package ru.eosreign.util.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.eosreign.client.MinioCommonClient;
import ru.eosreign.exception.InternalException;
import ru.eosreign.exception.InvalidArgumentException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static ru.eosreign.exception.InvalidArgumentException.INVALID_IMAGE_CONTENT_TYPE;

@Service("imageService")
@Primary
public class ImageService implements IFileService {

    @Value("${minio.user.access.url}")
    private String minioOuterUrl;

    private final MinioCommonClient minioClient;

    private static final Map<String, String> mimeTypeToExtensionMap = new HashMap<>();
    static {
        mimeTypeToExtensionMap.put("image/jpeg", ".jpg");
        mimeTypeToExtensionMap.put("image/jpg", ".jpg");
        mimeTypeToExtensionMap.put("image/png", ".png");
    }

    public ImageService(MinioCommonClient minioClient) {
        this.minioClient = minioClient;
    }

    public String setFile(String fileId,
                          MultipartFile image,
                          String bucketName,
                          String filePath) {
        try {
            String contentType = image.getContentType();
            String fileName = generateImageName(String.valueOf(fileId), contentType);
            minioClient.putObject(
                    filePath + fileName,
                    bucketName,
                    image.getInputStream(),
                    contentType
            );
            return minioOuterUrl + '/' + bucketName + filePath + fileName;
        } catch (IOException e) {
            throw new InternalException(e.getMessage());
        } catch (InternalException e) {
            throw new InternalException("Error while uploading image to MinIO: " + e.getMessage());
        }
    }

    public String updateFile(String fileId,
                             MultipartFile file,
                             String oldFileLink,
                             String bucketName,
                             String filePath) {
        try {
            String contentType = file.getContentType();
            String fileName = generateImageName(fileId, contentType);
            deleteFile(oldFileLink, bucketName);
            minioClient.putObject(
                    filePath + fileName,
                    bucketName,
                    file.getInputStream(),
                    contentType
            );
            return minioOuterUrl + '/' + bucketName + filePath + fileName;
        } catch (IOException e) {
            throw new InternalException(e.getMessage());
        } catch (InternalException e) {
            throw new InternalException("Error while updating image in MinIO: " + e.getMessage());
        }
    }

    public void deleteFile(String oldFileLink, String bucketName) {
        String oldObjectName = getObjectNameFromFileLink(oldFileLink, bucketName);
        minioClient.removeObject(
                oldObjectName,
                bucketName
        );
    }

    private String generateImageName(String id, String contentType) {
        String extension = mimeTypeToExtensionMap.get(contentType);
        if (extension == null) {
            throw new InvalidArgumentException(INVALID_IMAGE_CONTENT_TYPE);
        }
        return id + mimeTypeToExtensionMap.get(contentType);
    }

    private String getObjectNameFromFileLink(String fileLink, String bucketName) {
        String uselessPath = minioOuterUrl + '/' + bucketName + '/';
        return fileLink.replace(uselessPath, "");
    }
}