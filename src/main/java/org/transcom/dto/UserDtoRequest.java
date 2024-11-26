package org.transcom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDtoRequest extends UserDto {

    private Long companyId;
    private List<String> roleNames;
    private List<UUID> truckIds;
    private List<UUID> ordersIds;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserDtoRequest that = (UserDtoRequest) o;

        return companyId.equals(that.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), companyId);
    }

}