package com.ivan0063.test.aws.s3binaryretrieve.controller;

import com.ivan0063.test.aws.s3binaryretrieve.service.S3AwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;
import java.util.Optional;

@Controller
public class AwsS3ObjectRecoveryController {
    @Autowired
    private S3AwsService s3AwsService;
    @Value("${aws.bucket.name}")
    private String bucketName;

    @GetMapping(value = "get/pdf/{fileName}/{pathfile}",produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody byte[] getPDFFromS3Bucket(@PathVariable String fileName,
                                                   @PathVariable(required = false) String pathfile) throws Exception {

        StringBuilder fileNameCompleted = new StringBuilder();
        // check if there is a path for the file and add to the key name
        if(pathfile != null){
            fileNameCompleted.append(pathfile);
            fileNameCompleted.append("/");
        }
        fileNameCompleted.append(fileName);

        // Try to retrieve the object if there is no preset it witll throw an exception
        ByteArrayInputStream objectRetrieved = s3AwsService.getFile(fileNameCompleted.toString(), bucketName);
        Optional.ofNullable(objectRetrieved)
                .orElseThrow(()-> new Exception("Its possible that the object did not exist, check the logs"));

        return objectRetrieved.readAllBytes();
    }
}
