package org.hotel.dao.impl;

import org.hotel.dao.api.ReservationDAO;
import org.hotel.entity.builder.Builder;
import org.hotel.entity.order.Reservation;
import org.hotel.exception.DAOException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class ReservationDAOImpl extends AbstractDAO<Reservation> implements ReservationDAO {

    private static final String TABLE_NAME = "reservation";

    private static final String GET_ALL_QUERY = """
            SELECT o.*,
                   u.*,
                   rl.*,
                   r.*,
                   rd.*,
                   c.*,
            FROM `reservation` AS o
                     JOIN user u on u.id = o.user_id
                     JOIN role rl on rl.id = u.role_id
                     JOIN room r on r.id = o.room_id
                     JOIN room_details rd on r.id = rd.id
                     JOIN class c on c.id = r.class_id
            """;
    private static final String FIND_BY_ID = GET_ALL_QUERY.concat(" WHERE o.id = ?;");
    private static final String GET_LIMIT_QUERY = GET_ALL_QUERY.concat(" LIMIT ?,?;");
    private static final String FIND_BY_USER_ID_QUERY = GET_ALL_QUERY + " WHERE u.id = ?;";

    private static final String SAVE_QUERY = """
            INSERT INTO `reservation` (id, user_id, room_id, total_price, arrival_date, departure_date, status)
            VALUES (?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE user_id = VALUES(user_id),
            room_id = VALUES(room_id),
            total_price = VALUES(total_price),
            arrival_date = VALUES(arrival_date),
            departure_date = VALUES(departure_date),
            person_amount = VALUES(person_amount),
            status = VALUES(status);
            """;

    public ReservationDAOImpl(Builder<Reservation> builder, Connection connection) {
        super(builder, connection);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public List<Reservation> getAll(int lim1, int lim2) throws DAOException {
        return executeQuery(GET_LIMIT_QUERY, lim1, lim2);
    }

    @Override
    public Optional<Reservation> getById(Integer id) throws DAOException {
        return executeForSingleResult(FIND_BY_ID,id);
    }

    @Override
    public List<Reservation> getByUserId(int id) throws DAOException {
        return executeQuery(FIND_BY_USER_ID_QUERY, id);
    }

    @Override
    public void save(Reservation entity) throws DAOException {
        Object[] parameters = {
                entity.getId(),
                entity.getUser().getId(),
                entity.getRoom().getId(),
                entity.getTotalPrice(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPersonAmount(),
                entity.getReservationStatus()
        };
        executeUpdate(SAVE_QUERY, parameters);
    }
}
