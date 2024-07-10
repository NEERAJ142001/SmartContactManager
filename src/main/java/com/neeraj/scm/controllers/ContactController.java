package com.neeraj.scm.controllers;

import com.neeraj.scm.entity.Contacts;
import com.neeraj.scm.entity.User;
import com.neeraj.scm.form.ContactFormData;
import com.neeraj.scm.form.SearchData;
import com.neeraj.scm.serviceInterface.AwsService;
import com.neeraj.scm.serviceInterface.ContactService;
import com.neeraj.scm.serviceInterface.UserService;
import com.neeraj.scm.utilities.LoggedInUser;
import com.neeraj.scm.utilities.MessageType;
import com.neeraj.scm.utilities.MessageUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user/contact")
public class ContactController {

    private final ContactService contactService;
    private final UserService userService;
    private final AwsService awsService;

    @Value("${pageSize}")
    private String pageSize;

    @Autowired
    public ContactController(ContactService contactService, UserService userService, AwsService awsService) {
        this.contactService = contactService;
        this.userService = userService;
        this.awsService = awsService;

    }

    // it will open the add contact page
    @RequestMapping("/add")
    public String contactForm(Model model) {
        ContactFormData contactFormData = new ContactFormData();
        model.addAttribute("contactData", contactFormData);
        return "/contact/contactForm";
    }

    // action when user add the new contact
    @PostMapping("/addContact")
    public String addContact(@Valid @ModelAttribute("contactData") ContactFormData data,
                             BindingResult bindingResult,
                             Authentication authentication,
                             HttpSession session) {

        if (bindingResult.hasErrors()) {
            MessageUtility contactMessage = MessageUtility.builder().message("Please Correct the following errors")
                    .type(MessageType.red).build();
            session.setAttribute("message", contactMessage);
            return "/contact/contactForm";
        }
//        getting the current user who is logged it #userEmail
        String userEmail = LoggedInUser.getLoggedInUser(authentication);
        User user = userService.getUserByEmail(userEmail);


//        Creating object of Contact for storing the contact into database
        Contacts contact = new Contacts();
//        Storing image in the aws S3
        String fileUrl = null;
        if (data.getPicture() != null && !data.getPicture().isEmpty()) {
            fileUrl = awsService.uploadFile(data.getPicture(), data.getPhoneNumber());
            contact.setPicture(fileUrl);
        } else {
            contact.setPicture("/images/default_image.png");
        }
//        if (!contactService.isContactExist(data.getPhoneNumber())) {
        contact.setFirstName(data.getFirstName());
        contact.setLastName(data.getLastName());
        contact.setFavoriteContact(data.isFavoriteContact());
        contact.setEmail(data.getEmail());
        contact.setDescription(data.getDescription());
        contact.setPhoneNumber(data.getPhoneNumber());
        contact.setWebsiteLink(data.getWebsiteLink());
        contact.setAddress(data.getAddress());
        contact.setUser(user);
        contact.setLinkedInLink(data.getLinkedInLink());
        //        contact.setSocialLinks();
        MessageUtility contactMessage = MessageUtility.builder().message("Contact is successful registered")
                .type(MessageType.green).build();
        session.setAttribute("message", contactMessage);
        contactService.save(contact);
//        } else {
//            MessageUtility contactMessage = MessageUtility.builder().message("Contact is already registered with " +
//                    data.getPhoneNumber()).type(MessageType.red).build();
//            session.setAttribute("message", contactMessage);
//        }
        return "redirect:/user/contact/add";
    }

    //viewing all the contact List
    @GetMapping("/allContacts")
    public String viewAllContacts(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "5") int size,
                                  @RequestParam(value = "sort", defaultValue = "email") String sort,
                                  @RequestParam(value = "direction", defaultValue = "asc") String direction,
                                  Authentication authentication, Model model) {
//        getting all the contacts from the database for a specific user
        String currentLoggedInUser = LoggedInUser.getLoggedInUser(authentication);

        User user = userService.getUserByEmail(currentLoggedInUser);
        Page<Contacts> contacts = contactService.getContactsByUser(user, page, size, sort, direction);

        model.addAttribute("allContacts", contacts);
        SearchData searchData=new SearchData();
        model.addAttribute("search",searchData);
        model.addAttribute("pageSize", pageSize);
        return "/contact/allContacts";
    }

    //    getting all favourite contacts
    @GetMapping("/favourites")
    public String favorites(@RequestParam(value = "page", defaultValue = "0") int page,
                            @RequestParam(value = "size", defaultValue = "5") int size,
                            @RequestParam(value = "sort", defaultValue = "email") String sort,
                            @RequestParam(value = "direction", defaultValue = "asc") String direction,
                            Authentication authentication, Model model) {
        //        getting all the contacts from the database for a specific user
        String currentLoggedInUser = LoggedInUser.getLoggedInUser(authentication);

        User user = userService.getUserByEmail(currentLoggedInUser);
        Page<Contacts> contacts = contactService.getFavoriteContacts(user, page, size, sort, direction);
        model.addAttribute("allContacts", contacts);
        SearchData searchData=new SearchData();
        model.addAttribute("search",searchData);
        model.addAttribute("pageSize", pageSize);
        return "/contact/allFavoriteContacts";
    }

    //    searching the contact
    @GetMapping("/search")
    public String search(@ModelAttribute("search")SearchData data,
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "size", defaultValue = "5") int size,
                         @RequestParam(value = "sort", defaultValue = "email") String sort,
                         @RequestParam(value = "direction", defaultValue = "asc") String direction,
                         Authentication authentication,
                         Model model) {
        //        getting all the contacts from the database for a specific user
        String currentLoggedInUser = LoggedInUser.getLoggedInUser(authentication);
        User user=userService.getUserByEmail(currentLoggedInUser);
        String userChoice=data.getUserChoice().trim();

        // Determine the view name based on the search type
        String viewName = data.getType().toLowerCase().startsWith("fav") ? "contact/favSearch" : "contact/search";
        Page<Contacts> searchContacts = null;
        if (data.getType().equalsIgnoreCase("name")) {
            searchContacts = contactService.searchContactsByName(user, userChoice, page, size, sort, direction);
        } else if (data.getType().equalsIgnoreCase("email")) {
            searchContacts = contactService.searchContactsByEmail(user, userChoice, page, size, sort, direction);
        } else if (data.getType().equalsIgnoreCase("phone")) {
            searchContacts = contactService.searchContactsByPhoneNumber(user, userChoice, page, size, sort, direction );
        } else if (data.getType().equalsIgnoreCase("favName")) {
            searchContacts = contactService.searchContactsByFavName(user, userChoice, page, size, sort, direction);
        }else if (data.getType().equalsIgnoreCase("favPhone")) {
            searchContacts = contactService.searchContactsByFavPhoneNumber(user, userChoice, page, size, sort, direction);
        }else if (data.getType().equalsIgnoreCase("favEmail")) {
            searchContacts = contactService.searchContactsByFavEmail(user, userChoice, page, size, sort, direction);
        }
        model.addAttribute("allContacts", searchContacts);
        model.addAttribute("search",data);
        model.addAttribute("pageSize", pageSize);

        return viewName;
    }
}
