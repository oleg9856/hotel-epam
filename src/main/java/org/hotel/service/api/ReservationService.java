package org.hotel.service.api;


import org.hotel.entity.PageLimit;
import org.hotel.entity.order.Reservation;
import org.hotel.entity.room.Room;
import org.hotel.entity.room.RoomClass;
import org.hotel.entity.user.User;
import org.hotel.exception.ServiceException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationService {

    Optional<List<Reservation>> getAllReservations(PageLimit pageLimit) throws ServiceException;

    Optional<Reservation> getById(Integer id) throws ServiceException;

    Optional<List<Reservation>> getByUserId(Integer userId) throws ServiceException;

    void book(User user, LocalDate arrivalDate, LocalDate departureDate, RoomClass roomClass, int personsAmount)
            throws ServiceException;

    void approve(Integer id, Room room, BigDecimal totalPrice) throws ServiceException;

    void setPaid(Integer id) throws ServiceException;

    void setCheckedIn(Integer id) throws ServiceException;

    void setCheckedOut(Integer id) throws ServiceException;

    void cancel(Integer id) throws ServiceException;

}
