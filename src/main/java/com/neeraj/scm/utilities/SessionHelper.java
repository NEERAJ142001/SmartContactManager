package com.neeraj.scm.utilities;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SessionHelper {

    public static void removeMessageInSession() {
        System.out.println("session helper called");
        try {
            HttpSession session = ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes())
                    .getRequest()
                    .getSession(false);
            session.removeAttribute("message");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
