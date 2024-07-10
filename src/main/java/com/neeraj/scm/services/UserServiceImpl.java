package com.neeraj.scm.services;

import com.neeraj.scm.entity.User;
import com.neeraj.scm.globalExceptions.ResourceNotFoundExceptionByNeeraj;
import com.neeraj.scm.repositories.UserRepo;
import com.neeraj.scm.serviceInterface.UserService;
import com.neeraj.scm.utilities.RandomIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    @Value("${user.setDefaultRole}")
    private String setDefaultRole;


    @Autowired
    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        String id = RandomIdGenerator.randomIdGenerator();
        user.setUserId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoleList(List.of(setDefaultRole));
        return userRepo.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }

    @Override
    public Optional<User> updateUser(User updatedData) {
//        setting
        User user2;
        try {
            user2 = userRepo.findById(updatedData.getUserId()).orElseThrow(() -> new ResourceNotFoundExceptionByNeeraj("User not found"));
        } catch (ResourceNotFoundExceptionByNeeraj e) {
            throw new RuntimeException(e);
        }
        user2.setUserId(updatedData.getUserId());
        user2.setName(updatedData.getName());
        user2.setEmail(updatedData.getEmail());
        user2.setPassword(updatedData.getPassword());
        user2.setAbout(updatedData.getAbout());
        user2.setMobileNo(updatedData.getMobileNo());
        user2.setProfilePic(updatedData.getProfilePic());
        user2.setUserEnabled(updatedData.isUserEnabled());
        user2.setPhoneNoVerified(updatedData.isPhoneNoVerified());
        user2.setEmailVerified(updatedData.isEmailVerified());
        user2.setProviderUserId(updatedData.getProviderUserId());
        User user1 = userRepo.save(user2);
        return Optional.of(user1);
    }

    @Override
    public void deleteUser(String id) {
        User user2;
        try {
            user2 = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundExceptionByNeeraj("User Doesn't exist"));
        } catch (ResourceNotFoundExceptionByNeeraj e) {
            throw new RuntimeException(e);
        }
        userRepo.delete(user2);
    }


    @Override
    public boolean isUserExit(String id) {
        return userRepo.existsById(id);

    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
    @Override
    public long getCountOfContacts(User user) {
        return userRepo.countByUser(user);
    }
}
