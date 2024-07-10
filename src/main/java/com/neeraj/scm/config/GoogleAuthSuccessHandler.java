package com.neeraj.scm.config;

import com.neeraj.scm.entity.Providers;
import com.neeraj.scm.entity.User;
import com.neeraj.scm.repositories.UserRepo;
import com.neeraj.scm.utilities.RandomIdGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class GoogleAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private final UserRepo userRepo;

    @Value("${user.setDefaultRole}")
    private String setDefaultRole;

    @Autowired
    public GoogleAuthSuccessHandler(UserRepo userRepo) {
        this.userRepo = userRepo;

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // Log successful OAuth login
        log.info("User logged in with OAuth (Google): {}", authentication.getName());


        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        String provider = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oauthUser.getAttributes();

        User user = new User();
        user.setRoleList(List.of(setDefaultRole));
        user.setUserId(RandomIdGenerator.randomIdGenerator());
        user.setPassword("default_password");
        user.setUserEnabled(true);
        user.setEmailVerified(true);


        if (provider.equalsIgnoreCase("github")) {
            String email = (String) oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email") :
                    oauthUser.getAttribute("login") + "@github.com";
            String picture = (String) oauthUser.getAttribute("avatar_url");
            String name = (String) oauthUser.getAttribute("name");
            String providerUserId = oauthUser.getName();
            user.setProfilePic(picture);
            user.setEmail(email);
            user.setName(name);
            user.setAbout("This account is created through Github");
            user.setProviderUserId(providerUserId);
            user.setProviders(Providers.GITHUB);

            log.info("User provider and its email id and its name => {} and  {} and {}", user.getProviders(), user.getEmail(), user.getName());

        } else if (provider.equalsIgnoreCase("google")) {
            // Get user details from the authentication object
            String email = (String) attributes.get("email");
            String name = (String) attributes.get("name");
            String picture = (String) attributes.get("picture");
            String providerUserId = oauthUser.getName();
            user.setProfilePic(picture);
            user.setEmail(email);
            user.setName(name);
            user.setAbout("This account is created through Google");
            user.setProviderUserId(providerUserId);
            user.setProviders(Providers.GOOGLE);

            log.info("User provider and its email id => {} and  {}", user.getProviders(), user.getEmail());
            // Find existing user or create a new one
        }
        User existingUser = userRepo.findByEmail(user.getEmail()).orElse(null);
        if (existingUser == null) {
            userRepo.save(user);
        }
// Redirect to the user profile page
        redirectStrategy.sendRedirect(request, response, "/user/dashboard");
    }
}
