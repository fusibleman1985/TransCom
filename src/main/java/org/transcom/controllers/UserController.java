package org.transcom.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.transcom.dto.UserDtoRequest;
import org.transcom.dto.UserDtoResponse;
import org.transcom.services.UserService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDtoResponse> registerUser(@RequestBody @Valid UserDtoRequest userDto) {
        UserDtoResponse resultUser = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultUser);
    }

    @GetMapping
    public ResponseEntity<List<UserDtoResponse>> getAllUsers() {
        List<UserDtoResponse> users = userService.findAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDtoResponse> getUserById(@PathVariable UUID id) {
        UserDtoResponse userDtoRsponse = userService.findUserById(id);
        if (userDtoRsponse != null) {
            return ResponseEntity.ok(userDtoRsponse);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDtoResponse> updateUser(@PathVariable UUID id, @RequestBody @Valid UserDtoRequest userDto) {
        UserDtoResponse userDtoToUpdate = userService.findUserById(id);
        if (userDtoToUpdate != null) {
            UserDtoResponse updatedUser = userService.updateUser(id, userDto);
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
