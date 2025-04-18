package ru.eosreign.util.file;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

    String setFile(String fileId,
                   MultipartFile file,
                   String bucketName,
                   String filePath);

    String updateFile(String fileId,
                      MultipartFile file,
                      String oldFileLink,
                      String bucketName,
                      String filePath);

    void deleteFile(String fileLink,
                    String bucketName);
}