package org.transcom.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User Controller", description = "Controller for managing users")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    @Operation(summary = "Register a new user", description = "Creates a new user based on the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<UserDtoResponse> registerUser(@RequestBody @Valid UserDtoRequest userDto) {
        UserDtoResponse resultUser = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultUser);
    }

    @GetMapping("/get-all")
    @Operation(summary = "Get all users", description = "Returns a list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users successfully retrieved")
    })
    public ResponseEntity<List<UserDtoResponse>> getAllUsers() {
        List<UserDtoResponse> users = userService.findAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get user by ID", description = "Returns user details based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDtoResponse> getUserById(@PathVariable @ValidUUID UUID id) {
        UserDtoResponse userDtoResponse = userService.findUserById(id);

        if (userDtoResponse != null) {
            return ResponseEntity.status(HttpStatus.OK).body(userDtoResponse);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update user", description = "Updates user data based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully updated"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<UserDtoResponse> updateUser(@PathVariable @ValidUUID UUID id,
                                                      @RequestBody @Valid UserDtoRequest userDto) {
        UserDtoResponse userDtoToUpdate = userService.findUserById(id);
        if (userDtoToUpdate != null) {
            UserDtoResponse updatedUser = userService.updateUser(id, userDto);
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete user", description = "Deletes a user based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
