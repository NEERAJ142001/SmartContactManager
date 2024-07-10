package com.neeraj.scm.serviceInterface;

import com.neeraj.scm.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    User getUserByEmail(String email);

    void deleteUser(String id);

    Optional<User> updateUser(User user);

    boolean isUserExit(String id);

    boolean isUserExistByEmail(String email);

    List<User> getAllUsers();

    long getCountOfContacts(User user);


}
