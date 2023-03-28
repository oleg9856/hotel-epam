package org.hotel.dao.api;

import org.hotel.entity.room.Room;
import org.hotel.exception.DAOException;

import java.util.List;

public interface RoomDAO extends DAO<Room> {
    List<Room> getAllAndSorting(int lim1, int lim2, String orderBy) throws DAOException;
}
