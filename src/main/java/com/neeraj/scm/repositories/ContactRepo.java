package com.neeraj.scm.repositories;

import com.neeraj.scm.entity.Contacts;
import com.neeraj.scm.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepo extends JpaRepository<Contacts, String> {


    //     getting contact by user id
    Page<Contacts> findByUser(User user, Pageable pageable);

    //find the contacts list by the userid
    @Query("Select c from Contacts c where c.user.id = :userId")
    List<Contacts> findByUserId(@Param("userId") String userId);

    Contacts findByPhoneNumber(String phoneNumber);

    //getting favorite contacts
    Page<Contacts> findByFavoriteContactAndUser(boolean favoriteContact, User user, Pageable pageable);

    // methods for search by email name and phoneNumber
    Page<Contacts> findByUserAndFirstNameContaining(User user, String name, Pageable pageable);

    Page<Contacts> findByUserAndEmailContaining(User user, String email, Pageable pageable);

    Page<Contacts> findByUserAndPhoneNumberContaining(User user, String number, Pageable pageable);

    Page<Contacts> findByUserAndFavoriteContactAndFirstNameContaining(User user, boolean favoriteContact, String firstName, Pageable pageable);

    Page<Contacts> findByUserAndFavoriteContactAndEmailContaining(User user, boolean favoriteContact, String firstName, Pageable pageable);

    Page<Contacts> findByUserAndFavoriteContactAndPhoneNumberContaining(User user, boolean favoriteContact, String firstName, Pageable pageable);

}
