package com.neeraj.scm.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.neeraj.scm.serviceInterface.AwsService;
import com.neeraj.scm.utilities.FileConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URL;

@Service
public class AwsServiceImpl implements AwsService {

    private final AmazonS3 s3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Autowired
    public AwsServiceImpl(AmazonS3 s3) {
        this.s3 = s3;
    }

    @Override
    public String uploadFile(MultipartFile file, String fileName) {
        File fileObj = FileConvertor.convertMutiPartFileToFile(file);
        s3.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        fileObj.delete();
        return getFileUrl(fileName);
    }

    private String getFileUrl(String fileName) {
        URL url = s3.getUrl(bucketName, fileName);
        return url.toString();
    }
}
