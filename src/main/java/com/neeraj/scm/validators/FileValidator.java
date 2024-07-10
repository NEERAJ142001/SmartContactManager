package com.neeraj.scm.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileValidator implements ConstraintValidator<ImageValidator, MultipartFile> {
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 2; // 2MB

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            // If the file is null or empty, it's considered valid (optional field)
            return true;
        }

        // Check file size
        System.out.println("file size: " + file.getSize());
        if (file.getSize() > MAX_FILE_SIZE) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File size should be less than 2MB").addConstraintViolation();
            return false;
        }

        return true;
    }
}
