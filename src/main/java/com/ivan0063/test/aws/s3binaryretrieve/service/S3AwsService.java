package com.ivan0063.test.aws.s3binaryretrieve.service;

import com.ivan0063.test.aws.s3binaryretrieve.utils.Constants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.ByteArrayInputStream;

/**
 * <p>Service to provide an interrface to retrieve binary object from AWS S3</p>
 *
 * @author Ivan Mateos
 * @since 23/04/2021
 */
@Service
@Log4j2
public class S3AwsService {
    private S3Client s3Client;

    @Autowired
    public S3AwsService(Environment envContext) {
        // Get the region from the properties file
        Region regionObject = Region.of(envContext.getProperty(Constants.AWS_REGION));

        // Creation of the client to make request to AWS
        // In this case there is no need to setup credentials
        // it will retrieved those from the defualt chain
        this.s3Client = S3Client.builder()
                .region(regionObject)
                .build();
    }

    /**
     * <p>
     * Retrieve and object from a bucket on AWS and converts it into an
     * ByteArrayInputStream for its manipulation
     * </p>
     *
     * @param fileName
     * @param bucketName
     * @return ByteArrayInoutStream
     */
    public ByteArrayInputStream getFile(String fileName, String bucketName) {
        ByteArrayInputStream fileRetrieved = null;

        try {
            GetObjectRequest objectRequestToAWS = GetObjectRequest.builder()
                    .key(fileName)
                    .bucket(bucketName)
                    .build();

            ResponseBytes<GetObjectResponse> objectS3OnBytes = this.s3Client.getObjectAsBytes(objectRequestToAWS);
            byte[] dataRetrieved = objectS3OnBytes.asByteArray();

            fileRetrieved = new ByteArrayInputStream(dataRetrieved);

        } catch (S3Exception e) {
            log.error(e.getLocalizedMessage());
        }

        return fileRetrieved;
    }
}
