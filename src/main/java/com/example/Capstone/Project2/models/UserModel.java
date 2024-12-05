package com.example.Capstone.Project2.models;

import jakarta.persistence.*;
import lombok.Getter;

@Entity(name = "users")
@Getter
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String firstName;

    private String lastName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    private void setUsername(String username) {
        this.username = username.toLowerCase();
    }
}
