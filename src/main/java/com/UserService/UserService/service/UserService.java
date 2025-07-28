package com.UserService.UserService.service;

import com.UserService.UserService.JdbcConfig.UserJdbcRepository;
import com.UserService.UserService.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {


    @Autowired
    private UserJdbcRepository userJdbcRepository;

    public List<User> getAllUsers() {
        return userJdbcRepository.findAllUsers();
    }

    public User createUser(User user) {
        // Check for duplicate user by email (or username if applicable)
        if (userJdbcRepository.findUserByEmail(user.getEmail()) != null) {
            throw new com.UserService.UserService.ExceptionHandler.UserAlreadyExistsException("User with email '" + user.getEmail() + "' already exists.");
        }
        // Basic validation for user data
        if (user.getName() == null || user.getName().isEmpty() ||
            user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new com.UserService.UserService.ExceptionHandler.InvalidUserDataException("User name and email must not be empty.");
        }
        return userJdbcRepository.insertUser(user);
    }
    public User getUserById(Long id) {
        return userJdbcRepository.findUserById(id);   }

    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        // Basic validation for user data
        if (userDetails.getName() == null || userDetails.getName().isEmpty() ||
            userDetails.getEmail() == null || userDetails.getEmail().isEmpty()) {
            throw new com.UserService.UserService.ExceptionHandler.InvalidUserDataException("User name and email must not be empty.");
        }
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setRole(userDetails.getRole());
        return userJdbcRepository.updateUser(user);
    }
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userJdbcRepository.deleteUser(id);
    }
    public List<User> getUsersByRole(String role) {
        return userJdbcRepository.findAllUsers().stream()
                .filter(user -> user.getRole().equalsIgnoreCase(role))
                .toList();
    }
    public List<User> getUsersByName(String name) {
        return userJdbcRepository.findAllUsers().stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .toList();
    }
    public List<User> getUsersByEmail(String email) {
        return userJdbcRepository.findAllUsers().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .toList();
    }
}
