package org.hotel.dao.api;

import org.hotel.entity.order.Reservation;
import org.hotel.exception.DAOException;

import java.util.List;

public interface ReservationDAO extends DAO<Reservation> {
    List<Reservation> getByUserId(int id) throws DAOException;
}
