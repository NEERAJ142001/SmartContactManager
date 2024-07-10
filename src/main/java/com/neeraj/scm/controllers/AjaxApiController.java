package com.neeraj.scm.controllers;

import com.neeraj.scm.entity.Contacts;
import com.neeraj.scm.serviceInterface.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AjaxApiController {

    private final ContactService contactService;

    @Autowired
    public AjaxApiController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contact/{id}")
    public Contacts getContact(@PathVariable("id") String id) {
        return contactService.getContactById(id);
    }


}
