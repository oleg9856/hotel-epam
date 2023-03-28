package org.hotel.service.impl;


import org.hotel.dao.DAOHelper;
import org.hotel.dao.api.RoomClassDAO;
import org.hotel.entity.PageLimit;
import org.hotel.entity.builder.Builder;
import org.hotel.entity.builder.impl.RoomClassBuilder;
import org.hotel.entity.room.RoomClass;
import org.hotel.exception.DAOException;
import org.hotel.exception.ServiceException;
import org.hotel.service.api.RoomClassService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class RoomClassServiceImpl implements RoomClassService {

    private final RoomClassDAO dao;
    private final DAOHelper daoHelper;

    public RoomClassServiceImpl(DAOHelper daoHelper) {
        this.daoHelper = daoHelper;
        Builder<RoomClass> builder = new RoomClassBuilder();
        dao = daoHelper.roomClassDao(builder);
    }

    @Override
    public List<RoomClass> getAllRoomClasses(PageLimit pageLimit) throws ServiceException {
        try {
            int start = pageLimit.getItemsPerPage() * (pageLimit.getCurrentPage() - 1);
            return dao.getAll(start, pageLimit.getItemsPerPage());
        } catch (DAOException e) {
            throw new ServiceException();
        }
    }

    @Override
    public Optional<RoomClass> getByName(String name) throws ServiceException {
        try {
            return dao.getByName(name);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updatePrices(List<RoomClass> roomClasses) throws ServiceException {
        try {
            daoHelper.startTransaction();

            for (RoomClass submittedRoomClass : roomClasses) {
                String roomClassName = submittedRoomClass.getClassName();
                RoomClass actualRoomClass = dao.getByName(roomClassName)
                        .orElseThrow(() -> new ServiceException("Room class not found: " + roomClassName));
                BigDecimal basicRate = submittedRoomClass.getBasicRate();
                BigDecimal ratePerPerson = submittedRoomClass.getRatePerPerson();
                actualRoomClass.setBasicRate(basicRate);
                actualRoomClass.setRatePerPerson(ratePerPerson);
                dao.save(actualRoomClass);
            }

            daoHelper.endTransaction();
        } catch (Exception e) {
            try {
                daoHelper.cancelTransaction();
            } catch (DAOException daoException) {
                throw new ServiceException(daoException.getMessage(), e);
            }
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
