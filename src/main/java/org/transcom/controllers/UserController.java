package org.transcom.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.transcom.dto.UserDto;
import org.transcom.dto.UserDtoResponse;
import org.transcom.services.UserService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDtoResponse> registerUser(@RequestBody UserDto userDto) {
        UserDtoResponse resultUser = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.OK).body(resultUser);
    }

    @GetMapping
    public ResponseEntity<List<UserDtoResponse>> getAllUsers() {
        List<UserDtoResponse> users = userService.findAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDtoResponse> getUserById(@PathVariable UUID id) {
        UserDtoResponse user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<UserDtoResponse> updateUser(@PathVariable UUID id, @RequestBody User user) {
//        User resultUser = userService.updateUser(user);
//        return ResponseEntity.ok(resultUser);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
//        userService.deleteUser(id);
//        return ResponseEntity.noContent().build();
//    }
}
