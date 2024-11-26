package org.transcom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.transcom.entities.Company;
import org.transcom.entities.Order;
import org.transcom.entities.Role;
import org.transcom.entities.Truck;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDtoResponse extends UserDto {

    private UUID id;
    private Company company;
    private Set<Role> userRoles;
    private List<Truck> trucks;
    private List<Order> orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserDtoResponse that = (UserDtoResponse) o;

        return Objects.equals(id, that.id) &&
                company.equals(that.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}