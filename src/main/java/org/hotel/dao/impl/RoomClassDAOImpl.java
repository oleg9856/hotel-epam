package org.hotel.dao.impl;

import org.hotel.dao.api.RoomClassDAO;
import org.hotel.entity.builder.Builder;
import org.hotel.entity.room.RoomClass;
import org.hotel.exception.DAOException;

import java.sql.Connection;
import java.util.Optional;

public class RoomClassDAOImpl extends AbstractDAO<RoomClass> implements RoomClassDAO {

    private static final String TABLE_NAME = "room_class";
    private static final String FIND_BY_NAME_QUERY = "SELECT * FROM room_class WHERE class_name=?";
    private static final String SAVE_QUERY = """
            INSERT INTO class (id, class_name, basic_rate, rate_per_person)
            VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE class_name = VALUES(class_name),
            basic_rate = VALUES(basic_rate),
            rate_per_person = VALUES(rate_per_person);
            """;

    public RoomClassDAOImpl(Builder<RoomClass> builder, Connection connection) {
        super(builder, connection);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Optional<RoomClass> getByName(String name) throws DAOException {
        return executeForSingleResult(FIND_BY_NAME_QUERY, name);
    }

    @Override
    public void save(RoomClass entity) throws DAOException {
        Object[] parameters = {
                entity.getClassId(),
                entity.getClassName(),
                entity.getBasicRate(),
                entity.getRatePerPerson()
        };
        executeUpdate(SAVE_QUERY, parameters);
    }

}
