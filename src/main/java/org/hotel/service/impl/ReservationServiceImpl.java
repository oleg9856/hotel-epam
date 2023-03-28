package org.hotel.service.impl;

import lombok.extern.log4j.Log4j2;
import org.hotel.dao.DAOHelper;
import org.hotel.dao.api.ReservationDAO;
import org.hotel.entity.PageLimit;
import org.hotel.entity.builder.Builder;
import org.hotel.entity.builder.impl.ReservationBuilder;
import org.hotel.entity.order.Reservation;
import org.hotel.entity.order.ReservationStatus;
import org.hotel.entity.room.Room;
import org.hotel.entity.room.RoomClass;
import org.hotel.entity.user.User;
import org.hotel.exception.DAOException;
import org.hotel.exception.ServiceException;
import org.hotel.service.api.ReservationService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Log4j2
public class ReservationServiceImpl implements ReservationService {

    private final ReservationDAO dao;

    public ReservationServiceImpl(DAOHelper daoHelper) {
        Builder<Reservation> reservationBuilder = new ReservationBuilder();
        dao = daoHelper.orderDao(reservationBuilder);
    }

    @Override
    public List<Reservation> getAllReservations(PageLimit pageLimit) throws ServiceException {
        try {
            int start = pageLimit.getItemsPerPage() * (pageLimit.getCurrentPage() - 1);
            return dao.getAll(start, pageLimit.getItemsPerPage());
        } catch (DAOException e) {
            log.error("Error ---> {}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<Reservation> getById(Integer id) throws ServiceException {
        try {
            return dao.getById(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Reservation> getByUserId(Integer userId) throws ServiceException {
        try {
            return dao.getByUserId(userId);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void book(User user, LocalDate arrivalDate, LocalDate departureDate, RoomClass roomClass, int personsAmount)
            throws ServiceException {
        Reservation reservation = new Reservation();
        try {
            dao.save(reservation);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }



    @Override
    public void approve(Integer id, Room room, BigDecimal totalPrice) throws ServiceException {
        Reservation reservation = getByIdOrElseThrowException(id);
        ReservationStatus statusOrder = reservation.getReservationStatus();
        if (statusOrder != ReservationStatus.WAITING) {
            throw new ServiceException("Can't approve reservation which is " + statusOrder);
        }
        reservation.setRoom(room);
        reservation.setTotalPrice(totalPrice);
        reservation.setReservationStatus(ReservationStatus.APPROVED);
        try {
            dao.save(reservation);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void setPaid(Integer id) throws ServiceException {
        Reservation reservation = getByIdOrElseThrowException(id);
        ReservationStatus reservationStatus = reservation.getReservationStatus();
        if (reservationStatus != ReservationStatus.APPROVED) {
            throw new ServiceException("Can't set paid reservation which is " + reservationStatus);
        }
        reservation.setReservationStatus(ReservationStatus.PAID);
        try {
            dao.save(reservation);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void setCheckedIn(Integer id) throws ServiceException {
        Reservation reservation = getByIdOrElseThrowException(id);
        ReservationStatus reservationStatus = reservation.getReservationStatus();
        if (reservationStatus != ReservationStatus.PAID) {
            throw new ServiceException("Can't set checked in reservation which is " + reservationStatus);
        }
        reservation.setReservationStatus(
                ReservationStatus.CHECKED_IN);
        try {
            dao.save(reservation);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void setCheckedOut(Integer id) throws ServiceException {
        Reservation reservation = getByIdOrElseThrowException(id);
        ReservationStatus status = reservation.getReservationStatus();
        if (status != ReservationStatus.CHECKED_IN) {
            throw new ServiceException("Can't set checked out reservation which is " + status);
        }
        int statusId = ReservationStatus.CHECKED_OUT.ordinal() + 1;
        reservation.setReservationStatus(
                ReservationStatus.CHECKED_OUT);
        try {
            dao.save(reservation);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void cancel(Integer id) throws ServiceException {
        try {
            Optional<Reservation> optional = dao.getById(id);
            if (optional.isEmpty()) {
                throw new ServiceException("Reservation not found by id: " + id);
            }
            Reservation reservation = optional.get();
            ReservationStatus reservationStatus = reservation.getReservationStatus();
            if (reservationStatus == ReservationStatus.CANCELLED || reservationStatus == ReservationStatus.CHECKED_OUT) {
                throw new ServiceException("Can't cancel reservation which is " + reservationStatus);
            }
            reservation.setReservationStatus(
                    ReservationStatus.CANCELLED);
            dao.save(reservation);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private Reservation getByIdOrElseThrowException(int id) throws ServiceException {
        try {
            return dao.getById(id)
                    .orElseThrow(() -> new ServiceException("Reservation not found by id: " + id));
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
