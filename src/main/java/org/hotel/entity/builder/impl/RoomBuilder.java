package org.hotel.entity.builder.impl;

import org.hotel.entity.builder.Builder;
import org.hotel.entity.room.Room;
import org.hotel.entity.room.RoomClass;
import org.hotel.entity.room.RoomDetails;
import org.hotel.entity.room.StatusRoom;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomBuilder implements Builder<Room> {

    private static final String DEFAULT_ID_COLUMN = "id";
    private static final String CLASS_ID_COLUMN = "class_id";
    private static final String STATUS_ID_COLUMN = "status";
    private static final String NUMBER_PRICE_COLUMN = "number_price";
    private static final String CAPACITY_COLUMN = "beds_amount";


    private final String idAlias;
    private static final Builder<RoomClass> ROOM_CLASS_BUILDER
            = new RoomClassBuilder(CLASS_ID_COLUMN);
    private Builder<RoomDetails> roomDetailsBuilder;
    public RoomBuilder() {
        idAlias = DEFAULT_ID_COLUMN;
        getInitializeBuilder();
    }

    /**
     * Creates an instance of {@link RoomBuilder} with id alias.
     * This means, that id of {@link Room} will be obtained from {@link ResultSet} using this alias.
     *
     * @param idAlias alias for id column
     */
    public RoomBuilder(String idAlias) {
        this.idAlias = idAlias;
        getInitializeBuilder();
    }

    @Override
    public Room build(ResultSet resultSet) throws SQLException {
        int id = (int) resultSet.getObject(idAlias);
        if(resultSet.wasNull()){
            return null;
        }
        RoomClass roomClass = ROOM_CLASS_BUILDER.build(resultSet);
        StatusRoom statusRoom = StatusRoom
                .valueOf(resultSet.getString(STATUS_ID_COLUMN));
        BigDecimal price = resultSet.getBigDecimal(NUMBER_PRICE_COLUMN);
        short roomAmount = resultSet.getShort(CAPACITY_COLUMN);
        RoomDetails roomDetails = roomDetailsBuilder.build(resultSet);
        return Room.builder().id(id).roomClass(roomClass)
                .status(statusRoom).numberPrice(price)
                .bedsAmount(roomAmount).roomDetails(roomDetails).build();
    }

    private void getInitializeBuilder() {
        roomDetailsBuilder = new RoomDetailsBuilder(idAlias);
    }
}
