package com.neeraj.scm.utilities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MessageUtility {

    private String message;
    @Builder.Default
    private MessageType type = MessageType.blue;
}
