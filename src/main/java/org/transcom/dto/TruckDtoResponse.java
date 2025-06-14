package org.transcom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TruckDtoResponse extends TruckDto {

    private UUID id;

    private List<String> activeUsers;

    private List<UUID> favoriteByUserId;

}
