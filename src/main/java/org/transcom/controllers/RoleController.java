package org.transcom.controllers;

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
public class RoleController {

    private final RoleServiceImpl roleService;

    @PostMapping("/create")
    public ResponseEntity<RoleDto> create(@RequestBody @Valid RoleDto roleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.create(roleDto));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<RoleDto>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable @Valid Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getRoleById(id));
    }

    @GetMapping("/get/{nameRole}")
    public ResponseEntity<RoleDto> getRoleByName(@PathVariable String nameRole) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getRoleByName(nameRole));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<RoleDto> updateRole(@PathVariable @Valid Long id, @RequestBody RoleDto roleDto) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.updateRole(id, roleDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @Valid Long id) {
        if (roleService.delete(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
