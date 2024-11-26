package org.transcom.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.transcom.entities.enums.AccessRole;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleDto {

    @NotNull(message = "RoleName must not be null")
    private String roleName;

    @NotNull(message = "AccessRole must not be null")
    private AccessRole accessRole;

    @NotNull(message = "Description must not be null")
    private String description;

    @NotNull(message = "List<UUID> must not be null")
    private List<UUID> userIDs;

}