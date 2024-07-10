package com.neeraj.scm.globalExceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ApiResponse {

    private HttpStatus status;
    private String message;
    private boolean success;


}
