package com.UserService.UserService.dto;

import com.UserService.UserService.JdbcConfig.UserJdbcRepository;
import com.UserService.UserService.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl {

    @Autowired
    private UserJdbcRepository UserJdbcRepository;

    public UserDto createUser(UserDto userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setRole(userDTO.getRole());

        User saved = UserJdbcRepository.insertUser(user);

        UserDto response = new UserDto();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setEmail(saved.getEmail());
        response.setRole(saved.getRole());

        return response;
    }
    public UserDto getUserById(Long id) {
        User user = UserJdbcRepository.findUserById(id);
        if (user == null) {
            return null; // or throw an exception
        }
        UserDto response = new UserDto();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        return response;
    }

    public List<UserDto> getAllUsers() {
        List<User> users = UserJdbcRepository.findAllUsers();
        List<UserDto> dtos = new ArrayList<>();
        for (User user : users) {
            dtos.add(toDto(user));
        }
        return dtos;
    }

    public UserDto createUser(UserDto userDto) {
        User user = toEntity(userDto);
        User created = UserJdbcRepository.insertUser(user);
        return toDto(created);
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        User user = toEntity(userDto);
        user.setId(id);
        User updated = UserJdbcRepository.updateUser(user);
        return toDto(updated);
    }

    public void deleteUser(Long id) {
        UserJdbcRepository.deleteUser(id);
    }

    // Mapper methods
    private static UserDto toDto(User user) {
        if (user == null) return null;
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }

    private static User toEntity(UserDto dto) {
        if (dto == null) return null;
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        return user;
    }

}
