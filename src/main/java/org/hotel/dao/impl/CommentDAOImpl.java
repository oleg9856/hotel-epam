package org.hotel.dao.impl;

import org.hotel.dao.api.CommentDAO;
import org.hotel.entity.builder.Builder;
import org.hotel.entity.user.Comment;
import org.hotel.exception.DAOException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class CommentDAOImpl extends AbstractDAO<Comment> implements CommentDAO {

    private static final String TABLE_NAME = "comment";
    private static final String GET_ALL_QUERY = """
            SELECT co.*,
                   u.*,
                   rl.*,
                   r.*,
                   rd.*,
                   c.*,
                   sr.*
            FROM `comment` AS co
                     JOIN user u on u.id = co.user_id
                     JOIN role rl on rl.id = u.role_id
                     JOIN room r on r.id = co.room_id
                     JOIN room_details rd on r.id = rd.id
                     JOIN class c on c.id = r.class_id
                     JOIN status_room sr on sr.id = r.status_id
            """;
    private static final String GET_LIMIT_QUERY =
            GET_ALL_QUERY.concat(" LIMIT ?,?;");
    private static final String FIND_BY_ID_QUERY =
            GET_ALL_QUERY.concat(" WHERE co.id = ?;");

    private static final String SAVE_QUERY = """
            INSERT INTO comment (id, user_id, room_id, date_of_publication, description)
            VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE user_id = VALUES(user_id),
                                                       room_id = VALUES(room_id),
                                                       date_of_publication =VALUES(date_of_publication),
                                                       description = VALUES(description);
            """;

    public CommentDAOImpl(Builder<Comment> builder, Connection connection) {
        super(builder, connection);
    }

    @Override
    public List<Comment> getAll(int lim1, int lim2) throws DAOException {
        return executeQuery(GET_LIMIT_QUERY, lim1, lim2);
    }

    @Override
    public Optional<Comment> getById(Integer id) throws DAOException {
        return executeForSingleResult(FIND_BY_ID_QUERY, id);
    }

    @Override
    public void save(Comment entity) throws DAOException {
        Object[] parameters = {
                entity.getCommentId(),
                entity.getUser().getId(),
                entity.getRoom().getId(),
                entity.getDateOfPublication(),
                entity.getDescription()
        };
        executeUpdate(SAVE_QUERY, parameters);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
}
