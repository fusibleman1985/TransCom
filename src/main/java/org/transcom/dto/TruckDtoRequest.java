package org.transcom.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.transcom.entities.enums.TruckStatus;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TruckDtoRequest {

    @Min(message = "Length must be greater than zero", value = 1)
    private int length;

    @Min(message = "Weight must be greater than one", value = 1)
    private int weight;

    @Min(message = "Capacity must be greater than one", value = 1)
    private double capacity;

    @NotNull
    private TruckStatus truckStatus;

    @NotNull
    @NotEmpty
    private String location;

    @NotNull
    @NotEmpty
    private String truckTypeShortName;

    @NotNull
    @NotEmpty
    private List<UUID> listUserId;

//    @Mapper(componentModel = "spring")
//    public interface TruckMapper {
//
//        // Другие маппинг методы
//
//        @Mapping(target = "listUserId", source = "users")
//        TruckDto toDto(Truck truck);
//
//        @Mapping(target = "users", source = "listUserId")
//        Truck toEntity(TruckDto truckDto);
//
//        // Пользовательский метод для преобразования User -> UUID
//        default UUID map(User user) {
//            return user.getId(); // Предполагается, что у User есть поле UUID id
//        }
//
//        // Пользовательский метод для преобразования List<User> -> List<UUID>
//        default List<UUID> map(List<User> users) {
//            return users.stream()
//                    .map(User::getId)
//                    .collect(Collectors.toList());
//        }
//
//        // Пользовательский метод для преобразования List<UUID> -> List<User>
//        default List<User> mapFromUuid(List<UUID> userIds) {
//            // Пример: маппинг через репозиторий или фабрику
//            return userIds.stream()
//                    .map(id -> {
//                        User user = new User();
//                        user.setId(id);
//                        return user;
//                    })
//                    .collect(Collectors.toList());
//        }
//    }
//
//    @Mapper(componentModel = "spring")
//    public abstract class TruckMapper {
//
//        @Mapping(target = "listUserId", ignore = true) // Указать, что обрабатывается вручную
//        public abstract TruckDto toDto(Truck truck);
//
//        @AfterMapping
//        protected void mapUsersToDto(Truck truck, @MappingTarget TruckDto truckDto) {
//            List<UUID> userIds = truck.getUsers().stream()
//                    .map(User::getId)
//                    .collect(Collectors.toList());
//            truckDto.setListUserId(userIds);
//        }
//    }


}
