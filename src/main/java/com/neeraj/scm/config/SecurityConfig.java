package com.neeraj.scm.config;

import com.neeraj.scm.services.SecurityCustomUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final SecurityCustomUserDetailService securityCustomUserDetailService;
    private final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    private final GoogleAuthSuccessHandler googleAuthSuccessHandler;

    @Autowired
    public SecurityConfig(SecurityCustomUserDetailService securityCustomUserDetailService, GoogleAuthSuccessHandler googleAuthSuccessHandler) {
        this.securityCustomUserDetailService = securityCustomUserDetailService;
        this.googleAuthSuccessHandler = googleAuthSuccessHandler;
    }

    //    DAOAuthentication provider
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
//        setting user detail service
        authenticationProvider.setUserDetailsService(securityCustomUserDetailService);
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security.authorizeHttpRequests(authorize -> {
//            authorize.requestMatchers("/home","/login","/register","/").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
//            authorize.requestMatchers("/contact/**").authenticated();
            authorize.anyRequest().permitAll();
        });


        security.formLogin(formLogin -> {
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
//            formLogin.failureForwardUrl("/login?error=true");
//            formLogin.successForwardUrl("/user/dashboard");
            formLogin.successHandler((request, response, authentication) -> {
                // Log successful login
                logger.info("User logged in with email: {}", authentication.getName());
                response.sendRedirect("/user/dashboard");
            });
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");

        });
        security.logout(logout -> {
            logout.logoutUrl("/logout");
//            logout.logoutSuccessUrl("/login?logout=true");
            logout.logoutSuccessHandler((request, response, authentication) -> {
                // Log successful logout
                logger.info("User logged out: {}", authentication.getName());
                response.sendRedirect("/login?logout=true");
            });
            logout.invalidateHttpSession(true);
            logout.clearAuthentication(true);
            logout.deleteCookies("JSESSIONID");
        });
        security.csrf(AbstractHttpConfigurer::disable); // Temporarily disable CSRF protection for simplicity. Remove this in production.


//        oauth configuration
//       security.oauth2Login(Customizer.withDefaults());
        security.oauth2Login(google -> {
            google.loginPage("/login");
            google.successHandler(googleAuthSuccessHandler);
        });
        return security.build();
    }
}