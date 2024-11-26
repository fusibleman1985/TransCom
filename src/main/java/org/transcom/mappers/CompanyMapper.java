package org.transcom.mappers;

import org.mapstruct.*;
import org.transcom.dto.CompanyDtoRequest;
import org.transcom.dto.CompanyDtoResponse;
import org.transcom.entities.Company;
import org.transcom.entities.User;
import org.transcom.entities.enums.StringConstants;
import org.transcom.exceptions.UsersListIsNull;
import org.transcom.repositories.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {UserMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyMapper {

    @Mapping(target = "users", expression = "java(mapUserIdsToUsers(companyDtoRequest.getUserIds(), userRepository))")
    Company toEntity(CompanyDtoRequest companyDtoRequest, @Context UserRepository userRepository);

    @Mapping(target = "usersFullNameAndId", source = "users")
    CompanyDtoResponse toDtoResponse(Company company);

    //    @Named("mapUsersToUserIds")
    default List<String> mapUsersToUsersFullNameAndId(List<User> users) {
        if (users == null) {
            return Collections.emptyList();
        }
        return users.stream()
                .filter(Objects::nonNull)
                .map(user -> String.format("%s (%s)",
                        user.getFullName() != null ? user.getFullName() : StringConstants.UNKNOWN_USER.getValue(),
                        user.getId() != null ? user.getId().toString() : StringConstants.UNKNOWN_ID.getValue()))
                .collect(Collectors.toList());
    }

//    @Named("mapUsersToUserDtos")
//    default List<UserDtoResponse> mapUsersToUserDtos(List<User> users, UserMapper userMapper) {
//        return users.stream()
//                .map(userMapper::toUserDtoResponse)
//                .collect(Collectors.toList());
//    }

    default List<User> mapUserIdsToUsers(List<UUID> userIds, UserRepository userRepository) {
        if (userIds == null) {
            throw new UsersListIsNull("{error.users_list_is_null}");
        }
        return userRepository.findAllById(userIds);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", source = "userIds")
    default void updateCompanyFromDto(CompanyDtoRequest companyDtoRequest, @MappingTarget Company company, UserRepository userRepository) {
        if (companyDtoRequest.getCompanyName() != null && !companyDtoRequest.getCompanyName().isEmpty()) {
            company.setCompanyName(companyDtoRequest.getCompanyName());
        }
        if (companyDtoRequest.getCompanyRole() != null) {
            company.setCompanyRole(companyDtoRequest.getCompanyRole());
        }
        if (companyDtoRequest.getLicenseId() != null && !companyDtoRequest.getLicenseId().isEmpty()) {
            company.setLicenseId(companyDtoRequest.getLicenseId());
        }
        if (companyDtoRequest.getRating() != null) {
            company.setRating(companyDtoRequest.getRating());
        }
        if (companyDtoRequest.getCompanyStatus() != null) {
            company.setCompanyStatus(companyDtoRequest.getCompanyStatus());
        }
        company.setUsers(mapUserIdsToUsers(companyDtoRequest.getUserIds(), userRepository));
    }
}
