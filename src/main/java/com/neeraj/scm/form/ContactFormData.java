package com.neeraj.scm.form;

import com.neeraj.scm.validators.ImageValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ContactFormData {
    //    firstName lastName email phoneNumber favoriteContact websiteLink linkedInLink address picture description

    @NotBlank(message = "FirstName is required")
    private String firstName;

    @NotBlank(message = "LastName is required")
    private String lastName;

    @Email(message = "Invalid Email address")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(regexp = "(0/91)?[7-9][0-9]{9}", message = "Must be a valid phone number")
    private String phoneNumber;

    private boolean favoriteContact;

    private String websiteLink;

    private String linkedInLink;

    private String address;

    @ImageValidator
    private MultipartFile picture;

    private String description;

}
