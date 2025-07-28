package com.UserService.UserService.controller;

import com.UserService.UserService.dto.UserDto;
import com.UserService.UserService.model.User;
import com.UserService.UserService.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get all users")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUsers().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

//    how to make this method to create multiple users at once?
    @PostMapping("/bulk")
    public ResponseEntity<List<UserDto>> createUsers(@RequestBody List<UserDto> userDtos) {
        List<UserDto> createdUsers = userDtos.stream()
                .map(dto -> toDto(userService.createUser(toEntity(dto))))
                .collect(Collectors.toList());
        return ResponseEntity.ok(createdUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(toDto(userService.getUserById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(toDto(userService.updateUser(id, toEntity(userDto))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Mapping methods
    private UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }

    private User toEntity(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .role(dto.getRole())
                .build();
    }

}
