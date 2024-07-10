package com.neeraj.scm.serviceInterface;

import com.neeraj.scm.entity.Contacts;
import com.neeraj.scm.entity.User;
import com.neeraj.scm.globalExceptions.ResourceNotFoundExceptionByNeeraj;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContactService {

    Contacts save(Contacts contacts);

    boolean isContactExist(String phoneNumber);

    Contacts updateContact(Contacts contacts);

    List<Contacts> getAllContacts();

    Contacts getContactById(String id) throws ResourceNotFoundExceptionByNeeraj;

    void deleteContact(String id) throws ResourceNotFoundExceptionByNeeraj;


    List<Contacts> getContactByUserId(String userid);

    Page<Contacts> getContactsByUser(User user, int page, int size, String sort, String direction);

    Page<Contacts> getFavoriteContacts(User user, int page, int size, String sort, String direction);

    //    List<Contacts> searchContacts(String name, String email, String phoneNumber);
    Page<Contacts> searchContactsByName(User user, String name, int page, int size, String sort, String direction);

    Page<Contacts> searchContactsByEmail(User user, String email, int page, int size, String sort, String direction);

    Page<Contacts> searchContactsByPhoneNumber(User user, String number, int page, int size, String sort, String direction);

    Page<Contacts> searchContactsByFavPhoneNumber(User user, String number, int page, int size, String sort, String direction);

    Page<Contacts> searchContactsByFavEmail(User user, String number, int page, int size, String sort, String direction);

    Page<Contacts> searchContactsByFavName(User user, String number, int page, int size, String sort, String direction);
}
