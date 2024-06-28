package ucb.edu.bo.internship.internship_backend.service;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ucb.edu.bo.internship.internship_backend.dto.NewFileDto;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
@Service
public class MinioService {

    private final MinioClient minioClient;

    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public NewFileDto uploadFile(MultipartFile file, String bucket) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String fileName = UUID.randomUUID() + "." + file.getOriginalFilename().split("\\.")[file.getOriginalFilename().split("\\.").length - 1];
        minioClient.putObject(PutObjectArgs
                .builder()
                .bucket(bucket)
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .build());
        return new NewFileDto(fileName, file.getContentType(), bucket);
    }

    public String getFile(String bucket, String fileName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs
                .builder()
                .method(Method.GET)
                .bucket(bucket)
                .object(fileName)
                .build());
    }
}

