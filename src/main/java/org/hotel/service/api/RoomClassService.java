package org.hotel.service.api;

import org.hotel.entity.PageLimit;
import org.hotel.entity.room.RoomClass;
import org.hotel.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface RoomClassService {

    List<RoomClass> getAllRoomClasses(PageLimit roomSearch) throws ServiceException;
    Optional<RoomClass> getByName(String name) throws ServiceException;
    void updatePrices(List<RoomClass> roomClasses) throws ServiceException;
}
