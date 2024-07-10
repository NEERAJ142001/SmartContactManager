//package com.neeraj.scm.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//@Configuration
//public class SpringSecurityConfig {
//    //    InMemoryAuthentication is a feature in Spring Security that allows you to configure and manage user authentication
////    in-memory without the need for a database or external user store. This is particularly useful for simple applications,
////    prototypes, or testing scenarios where setting up a database might be unnecessary.
//
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.withUsername("user")
//                .password("{noop}password") // No password encoding
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.withUsername("admin")
//                .password("{noop}admin")
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }
//
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
//
//}