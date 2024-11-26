package org.transcom.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.transcom.dto.RoleDto;
import org.transcom.entities.Role;
import org.transcom.exceptions.RoleDeleteException;
import org.transcom.exceptions.RoleNotFoundException;
import org.transcom.mappers.RoleMapper;
import org.transcom.repositories.RoleRepository;
import org.transcom.repositories.UserRepository;
import org.transcom.services.RoleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Override
    public RoleDto create(RoleDto roleDto) {
        Role roleToSave = roleMapper.toEntity(roleDto, userRepository);
        return roleMapper.toDto(roleRepository.save(roleToSave));
    }

    @Override
    public List<RoleDto> getAll() {
        List<Role> allRoles = roleRepository.findAll();
        return allRoles.stream()
                .map(roleMapper::toDto)
                .toList();
    }

    @Override
    public RoleDto getRoleById(Long id) {
        Role roleById = returnRoleById(id);
        return roleMapper.toDto(roleById);
    }

    @Override
    public RoleDto getRoleByName(String roleName) {
        Role roleByName = roleRepository.findByRoleName(roleName);

        if (roleByName == null) {
            throw new RoleNotFoundException("{error.role_not_found}");
        }

        return roleMapper.toDto(roleByName);
    }

    @Override
    @Transactional
    public RoleDto updateRole(Long id, RoleDto roleDto) {
        Role roleToUpdate = returnRoleById(id);
        roleMapper.updateEntityFromDto(roleDto, roleToUpdate, userRepository);
        Role resultRole = roleRepository.save(roleToUpdate);
        return roleMapper.toDto(resultRole);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Role roleToDelete = returnRoleById(id);

        if (userRepository.existsUsersByUserRoles_RoleName(roleToDelete.getRoleName())) {
            throw new RoleDeleteException("{error.error_deleting_truck_type}");
        }

        roleRepository.delete(roleToDelete);
        return true;
    }

    private Role returnRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("{error.role_not_found}"));
    }
}
