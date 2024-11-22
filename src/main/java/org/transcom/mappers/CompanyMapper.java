package org.transcom.mappers;

import org.mapstruct.*;
import org.transcom.dto.CompanyDtoRequest;
import org.transcom.entities.Company;
import org.transcom.entities.User;
import org.transcom.repositories.UserRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CompanyMapper {

    @Mapping(target = "users", expression = "java(mapUserIdsToUsers(companyDtoRequest.getUserIds(), userRepository))")
    Company toEntity(CompanyDtoRequest companyDtoRequest, @Context UserRepository userRepository);

//    @Mapping(target = "userIds", source = "users", qualifiedByName = "mapUsersToUserIds")
//    CompanyDtoRequest toDto(Company company);

    @Named("mapUsersToUserIds")
    default List<UUID> mapUsersToUserIds(List<User> users) {
        return users.stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }

//    @Named("mapUsersToUserDtos")
//    default List<UserDtoResponse> mapUsersToUserDtos(List<User> users, UserMapper userMapper) {
//        return users.stream()
//                .map(userMapper::toUserDtoResponse)
//                .collect(Collectors.toList());
//    }

    default List<User> mapUserIdsToUsers(List<UUID> userIds, @Context UserRepository userRepository) {
        if (userIds == null) {
            return null;
        }
        return userRepository.findAllById(userIds);
    }

    // Метод для обновления компании, исключая поле "users"
    @Mapping(target = "users", ignore = true)
    void updateCompanyFromDto(CompanyDtoRequest companyDtoRequest, @MappingTarget Company company, @Context UserRepository userRepository);
}
