package org.hotel.entity.builder.impl;

import org.hotel.entity.builder.Builder;
import org.hotel.entity.room.RoomDetails;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomDetailsBuilder implements Builder<RoomDetails> {

    private static final String DEFAULT_ID_COLUMN = "id";
    private static final String DESCRIPTION_COLUMN = "description";
    private static final String ROOM_NUMBER_COLUMN = "room_number";
    private final String idAlias;

    public RoomDetailsBuilder() {
        idAlias = DEFAULT_ID_COLUMN;
    }

    /**
     * Creates an instance of {@link RoomDetailsBuilder} with id alias.
     * This means, that id of {@link RoomDetails} will be obtained from {@link ResultSet} using this alias.
     *
     * @param idAlias alias for id column
     */
    public RoomDetailsBuilder(String idAlias) {
        this.idAlias = idAlias;
    }

    @Override
    public RoomDetails build(ResultSet resultSet) throws SQLException {
        int id = (int) resultSet.getObject(idAlias);
        if (resultSet.wasNull()){
            return null;
        }
        String description = resultSet.getString(DESCRIPTION_COLUMN);
        String roomNumber = resultSet.getString(ROOM_NUMBER_COLUMN);
        return new RoomDetails(id, description, roomNumber);
    }
}
