package org.hotel.utils;

import org.hotel.entity.order.Reservation;
import org.hotel.entity.order.ReservationStatus;
import org.hotel.entity.room.Room;
import org.hotel.entity.room.RoomClass;
import org.hotel.entity.room.StatusRoom;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class RoomUtils {

    private final DateUtils dateUtils;

    public RoomUtils(DateUtils dateUtils) {
        this.dateUtils = dateUtils;
    }

    public List<Room> getAvailableRooms(List<Room> rooms, List<Reservation> reservations, Reservation reservation) {
        LocalDate arrivalDate = reservation.getStartDate();
        LocalDate departureDate = reservation.getEndDate();

        return rooms.stream()
                .filter(r -> r.getStatus() == StatusRoom.FREE &&
                        isRoomSuitable(r, reservation) &&
                        isRoomFree(r, arrivalDate, departureDate, reservations))
                .collect(Collectors.toList());
    }

    public boolean isRoomSuitable(Room room, Reservation reservation) {
        RoomClass preferredRoomClass = reservation.getRoom().getRoomClass();
        RoomClass roomClass = room.getRoomClass();
        return room.getBedsAmount().shortValue()
                == reservation.getPersonAmount().shortValue() &&
                roomClass.equals(preferredRoomClass);
    }

    public boolean isRoomFree(Room room, LocalDate arrivalDate, LocalDate departureDate, List<Reservation> allReservations) {
        return allReservations.stream()
                .filter(r ->
                        r.getReservationStatus()
                                != ReservationStatus.CHECKED_OUT &&
                        r.getReservationStatus()
                                != ReservationStatus.CANCELLED &&
                        r.getRoom() != null && r.getRoom().equals(room))
                .noneMatch(r -> dateUtils.isBetweenDates(arrivalDate, r.getStartDate(), r.getEndDate()) &&
                        dateUtils.isBetweenDates(departureDate, r.getStartDate(), r.getEndDate()));
    }

}
