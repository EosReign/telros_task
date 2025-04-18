package ru.eosreign.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Value("${minio.host}")
    private String minioUrl;
    @Value("${minio.port}")
    private int port;
    @Value("${minio.access.key}")
    private String accessKey;
    @Value("${minio.secret.key}")
    private String secretKey;
    @Value("${minio.secure}")
    private Boolean minioSecure;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .credentials(accessKey, secretKey)
                .endpoint(minioUrl, port, minioSecure)
                .build();
    }
}