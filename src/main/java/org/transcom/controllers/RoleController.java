package org.transcom.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.transcom.dto.RoleDto;
import org.transcom.services.impl.RoleServiceImpl;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@Validated
@Tag(name = "Role Controller", description = "Controller for managing roles")
public class RoleController {

    private final RoleServiceImpl roleService;

    @PostMapping("/create")
    @Operation(summary = "Create a new role", description = "Creates a new role based on the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Role successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<RoleDto> create(@RequestBody @Valid RoleDto roleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.create(roleDto));
    }

    @GetMapping("/get-all")
    @Operation(summary = "Get all roles", description = "Returns a list of all roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles successfully retrieved")
    })
    public ResponseEntity<List<RoleDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getAll());
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get a role by ID", description = "Returns a role based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    public ResponseEntity<RoleDto> getRoleById(@PathVariable @Valid Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getRoleById(id));
    }

    @GetMapping("/get/{nameRole}")
    @Operation(summary = "Get a role by name", description = "Returns a role based on the provided name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    public ResponseEntity<RoleDto> getRoleByName(@PathVariable String nameRole) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getRoleByName(nameRole));
    }

    @PatchMapping("/update/{id}")
    @Operation(summary = "Update a role", description = "Updates the data of a role based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role successfully updated"),
            @ApiResponse(responseCode = "404", description = "Role not found"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<RoleDto> updateRole(@PathVariable @Valid Long id, @RequestBody RoleDto roleDto) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.updateRole(id, roleDto));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a role", description = "Deletes a role based on the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Role successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    public ResponseEntity<Void> deleteById(@PathVariable @Valid Long id) {
        if (roleService.delete(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
