package org.hotel.entity.order;

import lombok.*;
import org.hotel.entity.Entity;
import org.hotel.entity.room.Room;
import org.hotel.entity.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation implements Entity {
    private Integer id;
    private User user;
    private Room room;
    private BigDecimal totalPrice;
    private LocalDate startDate;
    private LocalDate endDate;
    private Short personAmount;
    private ReservationStatus reservationStatus;
}
