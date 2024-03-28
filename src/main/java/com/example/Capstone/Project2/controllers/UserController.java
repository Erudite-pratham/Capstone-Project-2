package com.example.Capstone.Project2.controllers;

import com.example.Capstone.Project2.exceptions.UserAlreadyExistsException;
import com.example.Capstone.Project2.exceptions.UserNotFoundException;
import com.example.Capstone.Project2.models.UserModel;
import com.example.Capstone.Project2.repositories.UserRepository;
import com.example.Capstone.Project2.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private Map<String, Object> sanitizeUser(UserModel userModel) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", userModel.getId());
        map.put("firstName", userModel.getFirstName());
        map.put("lastName", userModel.getLastName());
        map.put("username", userModel.getUsername());
        map.put("phoneNumber", userModel.getPhoneNumber());
        return map;
    }

    @PostMapping("user/signup")
    public ResponseEntity<Object> signUpUser(@RequestBody UserModel userModel) {
        if (userRepository.findByPhoneNumber(userModel.getPhoneNumber()) != null || userRepository.findByUsername(userModel.getUsername()) != null) {
            throw new UserAlreadyExistsException();
        } else {
            UserModel savedUser = userRepository.save(userModel);
            Map<String, Object> sanitizedUser = sanitizeUser(savedUser);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("User signed up successfully", true, sanitizedUser));
        }
    }

    @GetMapping("user/{username}")
    public ResponseEntity<Object> getUserById(@PathVariable("username") String username) {
        UserModel foundUser = userRepository.findByUsername(username);
        if (foundUser != null) {
            Map<String, Object> sanitizedUser = sanitizeUser(foundUser);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("User found", true, sanitizedUser));
        } else {
            throw new UserNotFoundException();
        }

    }

    @GetMapping("getAllUsers")
    public ResponseEntity<Object> getAllUsers() {
        List<UserModel> foundUsers = userRepository.findAll();
        List<Map<String, Object>> sanitizedUsers = foundUsers.stream()
                .map(this::sanitizeUser)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new ApiResponse("All users", true, sanitizedUsers));

    }

    @PutMapping("user/update/{username}")
    public ResponseEntity<Object> updateUser(@PathVariable("username") String username, @RequestBody UserModel updatedUser) {
        int result = userRepository.updateUser(username, updatedUser.getFirstName(), updatedUser.getLastName(), updatedUser.getPassword());
        if (result == 1) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("User updated successfully", true, null));
        } else {
            throw new UserNotFoundException();
        }
    }


    @DeleteMapping("user/delete/{username}")
    public ResponseEntity<Object> deleteUserByUsername(@PathVariable("username") String username) {
        UserModel user = userRepository.findByUsername(username);
        if (user != null) {
            userRepository.delete(user);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse("User deleted successfully", true, null));
        } else {
            throw new UserNotFoundException();
        }
    }
}
