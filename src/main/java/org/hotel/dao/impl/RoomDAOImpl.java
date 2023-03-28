package org.hotel.dao.impl;

import lombok.extern.log4j.Log4j2;
import org.hotel.dao.api.RoomDAO;
import org.hotel.entity.builder.Builder;
import org.hotel.entity.room.Room;
import org.hotel.exception.DAOException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@Log4j2
public class RoomDAOImpl extends AbstractDAO<Room> implements RoomDAO {

    private static final String TABLE_NAME = "room";

    private static final String SAVE_QUERY = """
            INSERT INTO `room` (id, class_id, status_id, capacity, price)
            VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE id = VALUES(id),
            class_id = VALUES(class_id),
            status = VALUES(status),
            number_price = VALUES(number_price),
            beds_amount = VALUES(beds_amount);
            """;
    private static final String GET_ALL_QUERY = """
            SELECT r.*, rd.*, c.*, sr.* FROM room AS r
                JOIN room_details rd on r.id = rd.id
                JOIN class c on c.id = r.class_id
            """;
    private static final String LIMIT_QUERY = """
            LIMIT ?,?;
           """;

    private static final String ORDER_BY_CRITERIA = GET_ALL_QUERY + """
             ORDER BY ?
            """ + LIMIT_QUERY;
    private static final String FIND_BY_ID_QUERY = GET_ALL_QUERY.concat("""
             WHERE r.id = ?
            """);


    public RoomDAOImpl(Builder<Room> builder, Connection connection) {
        super(builder, connection);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Optional<Room> getById(Integer id) throws DAOException {
        return executeForSingleResult(FIND_BY_ID_QUERY, id);
    }

    public List<Room> getAllAndSorting(int lim1, int lim2, String orderBy) throws DAOException {
        return executeQuery(ORDER_BY_CRITERIA, lim1, lim2,
                orderBy);
    }

    @Override
    public void save(Room entity) throws DAOException {
        Object[] parameters = {
                entity.getId(),
                entity.getRoomClass().getClassId(),
                entity.getStatus(),
                entity.getNumberPrice(),
                entity.getBedsAmount()
        };
        executeUpdate(SAVE_QUERY, parameters);
    }


}
