package com.example.service;

import com.example.entity.User;

import java.util.Optional;

public interface UserService {

    Integer saveUser(User user);

    Optional<User> getUserByName(String name);
}
