package org.hotel.dao;


import org.hotel.dao.api.*;
import org.hotel.dao.impl.*;
import org.hotel.entity.builder.Builder;
import org.hotel.entity.order.Reservation;
import org.hotel.entity.room.Room;
import org.hotel.entity.room.RoomClass;
import org.hotel.entity.room.RoomDetails;
import org.hotel.entity.user.Role;
import org.hotel.entity.user.User;
import org.hotel.exception.DAOException;

import java.sql.Connection;
import java.sql.SQLException;

public class DAOHelper {

    private final Connection connection;

    public DAOHelper(Connection connection) {
        this.connection = connection;
    }

    public UserDAO userDao(Builder<User> builder) {
        return new UserDAOImpl(builder, connection);
    }

    public RoomClassDAO roomClassDao(Builder<RoomClass> builder) {
        return new RoomClassDAOImpl(builder, connection);
    }

    public RoomDAO roomDao(Builder<Room> builder) {
        return new RoomDAOImpl(builder, connection);
    }

    public RoomDetailsDAO roomDetailsDao(Builder<RoomDetails> builder){
        return new RoomDetailsDAOImpl(builder, connection);
    }

    public ReservationDAO orderDao(Builder<Reservation> builder) {
        return new ReservationDAOImpl(builder, connection);
    }

    public void startTransaction() throws DAOException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    public void endTransaction() throws DAOException {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    public void cancelTransaction() throws DAOException {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

}
