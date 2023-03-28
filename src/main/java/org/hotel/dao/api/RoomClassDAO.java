package org.hotel.dao.api;

import org.hotel.entity.room.RoomClass;
import org.hotel.exception.DAOException;

import java.util.Optional;

public interface RoomClassDAO extends DAO<RoomClass> {

    public Optional<RoomClass> getByName(String name) throws DAOException;

}
