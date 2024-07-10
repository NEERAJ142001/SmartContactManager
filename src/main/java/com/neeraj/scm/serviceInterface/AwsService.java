package com.neeraj.scm.serviceInterface;

import org.springframework.web.multipart.MultipartFile;


public interface AwsService {
    String uploadFile(MultipartFile file, String fileName);
}
