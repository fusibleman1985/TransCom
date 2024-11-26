package org.transcom.mappers;

import org.mapstruct.*;
import org.transcom.dto.RoleDto;
import org.transcom.entities.Role;
import org.transcom.entities.User;
import org.transcom.exceptions.UserNotFoundException;
import org.transcom.exceptions.UsersListIsNull;
import org.transcom.repositories.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        imports = {UUID.class, UserRepository.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    @Mapping(target = "users", ignore = true)
    Role toEntity(RoleDto roleDto);

    default Role toEntity(RoleDto roleDto, UserRepository userRepository) {
        Role role = toEntity(roleDto);
        role.setUsers(mapUserIDsToUsers(roleDto.getUserIDs(), userRepository));
        return role;
    }

    default List<User> mapUserIDsToUsers(List<UUID> userIds, UserRepository userRepository) {
        if (userIds == null) {
            throw new UsersListIsNull("{error.users_list_is_null}");
        }
        List<User> users = userRepository.findAllById(userIds);
        if (users.size() != userIds.size()) {
            throw new UserNotFoundException("{error.user_not_found}");
        }
        return users;
    }

    @Mapping(target = "userIDs", source = "users")
    RoleDto toDto(Role role);

    default List<UUID> mapUsersToUserIDs(List<User> users) {
        return users.stream()
                .filter(Objects::nonNull)
                .map(User::getId)
                .toList();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userIDs", source = "users")
    default void updateEntityFromDto(RoleDto roleDto, @MappingTarget Role role, UserRepository userRepository) {
        if (roleDto.getRoleName() != null && !roleDto.getRoleName().isEmpty()) {
            role.setRoleName(roleDto.getRoleName());
        }
        if (role.getAccessRole() != null) {
            role.setAccessRole(roleDto.getAccessRole());
        }
        if (role.getDescription() != null && !role.getDescription().isEmpty()) {
            role.setDescription(roleDto.getDescription());
        }
        role.setUsers(mapUserIDsToUsers(roleDto.getUserIDs(), userRepository));
    }
}
