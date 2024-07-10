package com.neeraj.scm.controllers;

import com.neeraj.scm.entity.User;
import com.neeraj.scm.form.UserFormData;
import com.neeraj.scm.serviceInterface.UserService;
import com.neeraj.scm.utilities.MessageType;
import com.neeraj.scm.utilities.MessageUtility;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    private final UserService userService;

    //    default value of image
    @Value("${user.profilePic.default}")
    private String defaultProfilePic;

    //    constructor for injecting beans
    @Autowired
    HomeController(UserService userService) {
        this.userService = userService;
    }

    //home page
    @RequestMapping("/")
    public String index() {
        return "redirect:/home";
    }

    //home page
    @RequestMapping("/home")
    public String home() {
        System.out.println("home page");
        return "home";
    }

    //about page
    @RequestMapping("/about")
    public String hand() {
        System.out.println("About page");
        return "about";
    }

    // Services page
    @RequestMapping("/services")
    public String services() {
        System.out.println("Service page");
        return "services";
    }

    //Contact-us page
    @RequestMapping("/contact")
    public String contact() {
        System.out.println("Service page");
        return "contact";
    }

    //  login page
    @GetMapping("/login")
    public String login() {
        System.out.println("login page");
        return "login";
    }

    //when user logout it will take to login page
    @PostMapping("/logout")
    public String logout() {
        System.out.println("logout page");
        return "login";
    }

    //signup page action =>register
    @RequestMapping("/register")
    public String register(Model model) {
//      data-th-object="${data}" || data-th-field="*{password}"  set the both name and value automatically
        UserFormData data = new UserFormData();
        model.addAttribute("data", data);
        System.out.println("Service page");
        return "register";
    }

    //    form processing when user is registering
    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("data") UserFormData data, BindingResult bindingResult, HttpSession session) {

        // Checking form validation, if any errors arise, it will redirect to the same page
        if (bindingResult.hasErrors()) {
            return "register";
        }
//        if there is no error then it will create a new user
//        Check if the user is already registered
        if (userService.isUserExistByEmail(data.getEmail())) {
            MessageUtility greenMessage = MessageUtility.builder()
                    .message("User is Already Registered")
                    .type(MessageType.red)
                    .build();
            session.setAttribute("message", greenMessage);
            return "redirect:/register";
        } else {
            User user = new User();
            user.setName(data.getName());
            user.setEmail(data.getEmail());
            user.setPassword(data.getPassword());
            user.setAbout(data.getAbout());
            user.setMobileNo(data.getMobileNo());
            user.setProfilePic((defaultProfilePic));

            User saveuser = userService.saveUser(user);
            MessageUtility greenMessage = MessageUtility.builder()
                    .message("Successfully Registered")
                    .type(MessageType.green)
                    .build();
            session.setAttribute("message", greenMessage);
        }
        System.out.println("successfully data saved");
        return "redirect:/register";
    }
}
