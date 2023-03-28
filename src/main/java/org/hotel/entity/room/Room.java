package org.hotel.entity.room;

import lombok.*;
import org.hotel.entity.Entity;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room implements Entity {
    private Integer id;
    private RoomClass roomClass;
    private StatusRoom status;
    private BigDecimal numberPrice;
    private Short bedsAmount;
    private RoomDetails roomDetails;
}
