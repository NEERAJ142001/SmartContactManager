package com.neeraj.scm.services;

import com.neeraj.scm.entity.Contacts;
import com.neeraj.scm.entity.User;
import com.neeraj.scm.globalExceptions.ResourceNotFoundExceptionByNeeraj;
import com.neeraj.scm.repositories.ContactRepo;
import com.neeraj.scm.serviceInterface.ContactService;
import com.neeraj.scm.utilities.RandomIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepo contactRepo;

    @Autowired
    public ContactServiceImpl(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }

    @Override
    public Contacts save(Contacts contacts) {
        String id = RandomIdGenerator.randomIdGenerator();
        contacts.setId(id);
        return contactRepo.save(contacts);
    }

    @Override
    public boolean isContactExist(String phoneNumber) {
        return contactRepo.findByPhoneNumber(phoneNumber) != null;
    }

    @Override
    public Contacts updateContact(Contacts contacts) {
//        incomplete
        return contactRepo.save(contacts);
    }

    @Override
    public List<Contacts> getAllContacts() {
        return contactRepo.findAll();
    }

    @Override
    public Contacts getContactById(String id) throws ResourceNotFoundExceptionByNeeraj {
        return contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundExceptionByNeeraj("Contact not found with given id :" + id));
    }

    @Override
    public void deleteContact(String id) throws ResourceNotFoundExceptionByNeeraj {
        Contacts contacts = contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundExceptionByNeeraj("Contact not found with given id :" + id));
        contactRepo.delete(contacts);
    }


    @Override
    public List<Contacts> getContactByUserId(String userid) {
        return contactRepo.findByUserId(userid);
    }

    @Override
    public Page<Contacts> getContactsByUser(User user, int page, int size, String sort, String direction) {
        Sort sort1 = direction.equals("desc") ? Sort.by(sort).descending() : Sort.by(sort).ascending();

        PageRequest pageable = PageRequest.of(page, size, sort1);
        return contactRepo.findByUser(user, pageable);
    }

    @Override
    public Page<Contacts> getFavoriteContacts(User user, int page, int size, String sort, String direction) {
        Sort sort1 = direction.equals("desc") ? Sort.by(sort).descending() : Sort.by(sort).ascending();

        PageRequest pageable = PageRequest.of(page, size, sort1);
        return contactRepo.findByFavoriteContactAndUser(true, user, pageable);
    }

    //searching method

    @Override
    public Page<Contacts> searchContactsByName(User user,String name,int page, int size, String sort, String direction) {
        Sort sort1 = direction.equals("desc") ? Sort.by(sort).descending() : Sort.by(sort).ascending();

        PageRequest pageable = PageRequest.of(page, size, sort1);
        return contactRepo.findByUserAndFirstNameContaining(user,name,pageable);
    }

    @Override
    public Page<Contacts> searchContactsByEmail(User user,String email,int page, int size, String sort, String direction) {
        Sort sort1 = direction.equals("desc") ? Sort.by(sort).descending() : Sort.by(sort).ascending();

        PageRequest pageable = PageRequest.of(page, size, sort1);
        return contactRepo.findByUserAndEmailContaining(user,email,pageable);
    }

    @Override
    public Page<Contacts> searchContactsByPhoneNumber(User user,String phoneNumber,int page, int size, String sort, String direction) {
        Sort sort1 = direction.equals("desc") ? Sort.by(sort).descending() : Sort.by(sort).ascending();

        PageRequest pageable = PageRequest.of(page, size, sort1);
        return contactRepo.findByUserAndPhoneNumberContaining(user,phoneNumber,pageable);
    }

    @Override
    public Page<Contacts> searchContactsByFavPhoneNumber(User user, String number, int page, int size, String sort, String direction) {
        Sort sort1 = direction.equals("desc") ? Sort.by(sort).descending() : Sort.by(sort).ascending();

        PageRequest pageable = PageRequest.of(page, size, sort1);
        return contactRepo.findByUserAndFavoriteContactAndPhoneNumberContaining(user,true,number,pageable);
    }

    @Override
    public Page<Contacts> searchContactsByFavEmail(User user, String email, int page, int size, String sort, String direction) {
        Sort sort1 = direction.equals("desc") ? Sort.by(sort).descending() : Sort.by(sort).ascending();

        PageRequest pageable = PageRequest.of(page, size, sort1);
        return contactRepo.findByUserAndFavoriteContactAndEmailContaining(user,true,email,pageable);
    }

    @Override
    public Page<Contacts> searchContactsByFavName(User user, String name, int page, int size, String sort, String direction) {
        Sort sort1 = direction.equals("desc") ? Sort.by(sort).descending() : Sort.by(sort).ascending();

        PageRequest pageable = PageRequest.of(page, size, sort1);
        return contactRepo.findByUserAndFavoriteContactAndFirstNameContaining(user,true,name,pageable);
    }
}
