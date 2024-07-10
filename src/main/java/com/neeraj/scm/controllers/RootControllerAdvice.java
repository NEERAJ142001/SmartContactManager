package com.neeraj.scm.controllers;

import com.neeraj.scm.entity.User;
import com.neeraj.scm.serviceInterface.UserService;
import com.neeraj.scm.utilities.LoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootControllerAdvice {

    private final UserService userService;

    @Autowired
    public RootControllerAdvice(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public void storeLoggedInUserInfo(Model model, Authentication authentication) {
        if (authentication==null)
            return;
        String name = LoggedInUser.getLoggedInUser(authentication);
        User fetchedUser = userService.getUserByEmail(name);

        model.addAttribute("username", fetchedUser);

    }
}
