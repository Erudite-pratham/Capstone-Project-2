package com.example.Capstone.Project2.repositories;

import com.example.Capstone.Project2.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<UserModel, Integer> {

    UserModel findByPhoneNumber(String phoneNumber);

    UserModel findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE users u SET u.first_name = :firstName, u.last_name = :lastName, u.password = :password WHERE u.username = :username", nativeQuery = true)
    int updateUser(@Param("username") String username, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("password") String password);
}
