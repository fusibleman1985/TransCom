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
import org.transcom.validation.annotations.ValidUUID;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserDtoResponse> registerUser(@RequestBody @Valid UserDtoRequest userDto) {
        UserDtoResponse resultUser = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultUser);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<UserDtoResponse>> getAllUsers() {
        List<UserDtoResponse> users = userService.findAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserDtoResponse> getUserById(@PathVariable @ValidUUID UUID id) {
        UserDtoResponse userDtoResponse = userService.findUserById(id);

        if (userDtoResponse != null) {
            return ResponseEntity.status(HttpStatus.OK).body(userDtoResponse);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDtoResponse> updateUser(@PathVariable @ValidUUID UUID id, @RequestBody @Valid UserDtoRequest userDto) {
        UserDtoResponse userDtoToUpdate = userService.findUserById(id);
        if (userDtoToUpdate != null) {
            UserDtoResponse updatedUser = userService.updateUser(id, userDto);
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
