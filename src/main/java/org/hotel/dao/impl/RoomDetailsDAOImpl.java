package org.hotel.dao.impl;

import org.hotel.dao.api.RoomDetailsDAO;
import org.hotel.entity.builder.Builder;
import org.hotel.entity.room.RoomDetails;
import org.hotel.exception.DAOException;

import java.sql.Connection;

public class RoomDetailsDAOImpl extends AbstractDAO<RoomDetails> implements RoomDetailsDAO {

    private static final String TABLE_NAME = "room_details";

    private static final String SAVE_QUERY = """
            INSERT INTO room_details(id, description, room_number, image)
            VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE id = VALUES(id),
            description = VALUES(description),
            room_number = VALUES(room_number);
            """;

    public RoomDetailsDAOImpl(Builder<RoomDetails> builder, Connection connection) {
        super(builder, connection);
    }

    @Override
    public void save(RoomDetails entity) throws DAOException {
        Object[] parameters = {
                entity.getRoomId(),
                entity.getDescription(),
                entity.getRoomNumber()
        };
        executeUpdate(SAVE_QUERY, parameters);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
}
