package org.transcom.services;

import org.transcom.dto.RoleDto;

import java.util.List;

public interface RoleService {

    RoleDto create(RoleDto dto);

    List<RoleDto> getAll();

    RoleDto getRoleById(Long id);

    RoleDto getRoleByName(String roleName);

    RoleDto updateRole(Long id, RoleDto roleDtoRequest);

    boolean delete(Long id);
}
