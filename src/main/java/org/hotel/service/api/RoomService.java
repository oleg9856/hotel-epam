package org.hotel.service.api;

import org.hotel.entity.room.Room;
import org.hotel.entity.room.RoomSearch;
import org.hotel.entity.room.StatusRoom;
import org.hotel.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RoomService {

    Optional<List<Room>> getAllRooms(RoomSearch roomSearch) throws ServiceException;
    void setStatusRoom(Integer id, StatusRoom status) throws ServiceException;
    Optional<Room> getById(Integer id) throws ServiceException;
    void saveOrUpdateRoom(Room room) throws ServiceException;
    void updatePrice(Integer id, BigDecimal price) throws ServiceException;

}
