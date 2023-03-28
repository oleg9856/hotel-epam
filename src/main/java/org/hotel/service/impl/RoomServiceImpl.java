package org.hotel.service.impl;

import lombok.extern.log4j.Log4j2;
import org.hotel.dao.DAOHelper;
import org.hotel.dao.api.RoomDAO;
import org.hotel.dao.api.RoomDetailsDAO;
import org.hotel.entity.builder.Builder;
import org.hotel.entity.builder.impl.RoomBuilder;
import org.hotel.entity.builder.impl.RoomDetailsBuilder;
import org.hotel.entity.room.Room;
import org.hotel.entity.room.RoomDetails;
import org.hotel.entity.room.RoomSearch;
import org.hotel.entity.room.StatusRoom;
import org.hotel.exception.DAOException;
import org.hotel.exception.ServiceException;
import org.hotel.service.api.RoomService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Log4j2
public class RoomServiceImpl implements RoomService {

    private final RoomDAO roomDAO;
    private final RoomDetailsDAO detailsDAO;

    public RoomServiceImpl(DAOHelper daoHelper) {
        Builder<RoomDetails> roomDetailsBuilder = new RoomDetailsBuilder();
        Builder<Room> roomBuilder = new RoomBuilder();

        this.detailsDAO = daoHelper.roomDetailsDao(roomDetailsBuilder);
        this.roomDAO = daoHelper.roomDao(roomBuilder);
    }

    @Override
    public Optional<Room> getById(Integer id) throws ServiceException {
        try {
            return roomDAO.getById(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void saveOrUpdateRoom(Room room) throws ServiceException {
        try {
            roomDAO.save(room);
            detailsDAO.save(room.getRoomDetails());
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void updatePrice(Integer id, BigDecimal price) throws ServiceException {
        try {
            Room room = roomDAO.getById(id)
                    .orElseThrow(() -> new ServiceException("Not found room by id"));
            roomDAO.save(room);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }


    @Override
    public List<Room> getAllRooms(RoomSearch roomSearch) throws ServiceException {
        try {
            int start = roomSearch.getItemsPerPage() * (roomSearch.getCurrentPage() - 1);
            return roomDAO.getAllAndSorting(start, roomSearch.getItemsPerPage(),
                    roomSearch.getOrderBy());
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void setStatusRoom(Integer id, StatusRoom status) throws ServiceException {
        try {
            Room room = roomDAO.getById(id)
                    .orElseThrow(() -> new ServiceException("Not found room by id"));
            room.setStatus(status);
            roomDAO.save(room);
        } catch (DAOException e) {
            log.error("Error in set status room method --> {}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
