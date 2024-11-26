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
public class RoleDtoRequest {

    @NotNull(message = "Role_name must ")
    private String roleName;

    @NotNull
    private AccessRole accessRole;

    @NotNull
    private String description;

    @NotNull
    private List<UUID> userIDs;
}