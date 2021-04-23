package com.ivan0063.test.aws.s3binaryretrieve.controller;

import com.ivan0063.test.aws.s3binaryretrieve.service.S3AwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AwsS3ObjectRecoveryController {
    @Autowired
    private S3AwsService s3AwsService;
    @Value("${aws.bucket.name}")
    private String bucketName;

    @GetMapping(value = "get/pdf/{fileName}",produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody byte[] getPDFFromS3Bucket(@PathVariable String fileName) {
        StringBuilder fileNameCompleted = new StringBuilder();
        fileNameCompleted.append("saludos/");
        fileNameCompleted.append(fileName);

        return s3AwsService.getFile(fileNameCompleted.toString(), bucketName).readAllBytes();
    }
}
