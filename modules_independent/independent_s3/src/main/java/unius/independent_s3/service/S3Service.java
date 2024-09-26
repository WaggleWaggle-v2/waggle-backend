package unius.independent_s3.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import unius.core_uuid.util.UuidUtils;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3Client amazonS3Client;

    public String uploadFile(MultipartFile file, String domain) {
        String fileUrl = bucket + "/" + domain + "/" + UuidUtils.generateUuid() + getExtension(file);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        try {
            amazonS3Client.putObject(bucket, fileUrl, file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return amazonS3Client.getUrl(bucket, fileUrl).toString();
    }

    private String getExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if(ObjectUtils.isEmpty(fileName)) {
            // TODO Exception 지정

            throw new RuntimeException();
        }

        return file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
    }
}