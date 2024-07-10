package com.neeraj.scm.form;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserFormData {
    @NotBlank(message = "Email id is mandatory")
    private String email;

    @Size(min = 3, message = "Username must be at least 3 character")
    private String name;


    @Pattern(regexp = "(0/91)?[7-9][0-9]{9}", message = "Must be a valid phone number")
    private String mobileNo;


    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "It must be at least 8 characters long and contain at least 1 uppercase letter, 1 lowercase letter, 1 digit, and 1 special character")
    private String password;

    private String about;
}
