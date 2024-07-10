package com.neeraj.scm.controllers;


import com.neeraj.scm.entity.User;
import com.neeraj.scm.serviceInterface.UserService;
import com.neeraj.scm.utilities.LoggedInUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //    user dashboard page
    @GetMapping("/dashboard")
    public String dashboard() {
        return "user/dashboard";
    }

    //    user profile page
    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {
//        getting the userId(email) of the logged in user
//        String name = LoggedInUser.getLoggedInUser(authentication);
//        User fetchedUser = userService.getUserByEmail(name);
//        model.addAttribute("username", fetchedUser);

        //        getting all the contacts from the database for a specific user
        String currentLoggedInUser = LoggedInUser.getLoggedInUser(authentication);

        User user=userService.getUserByEmail(currentLoggedInUser);
        model.addAttribute("userdata",user);
        long countOfContact=userService.getCountOfContacts(user);
        model.addAttribute("totalContact",countOfContact);
        return "user/profile";
    }
}
