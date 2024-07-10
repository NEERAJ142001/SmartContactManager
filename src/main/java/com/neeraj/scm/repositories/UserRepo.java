package com.neeraj.scm.repositories;

import com.neeraj.scm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);

    //    count of contact
// Method to count contacts for a specific user
    @Query("SELECT COUNT(c) FROM Contacts c WHERE c.user = :user")
    long countByUser(@Param("user") User user);
}
