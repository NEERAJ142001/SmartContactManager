package com.neeraj.scm.utilities;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class LoggedInUser {


//this method getting the email of the logged in user
    public static String getLoggedInUser(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            String provider = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            DefaultOAuth2User  oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
            String email = null;
            if ("google".equalsIgnoreCase(provider) || "github".equalsIgnoreCase(provider)) {
                email = oauthUser.getAttribute("email");
                return  email;
            }
        }
        return authentication.getName();
    }
}
