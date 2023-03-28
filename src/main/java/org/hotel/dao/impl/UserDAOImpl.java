package org.hotel.dao.impl;

import lombok.extern.log4j.Log4j2;
import org.hotel.dao.api.UserDAO;
import org.hotel.entity.builder.Builder;
import org.hotel.entity.user.User;
import org.hotel.exception.DAOException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@Log4j2
public class UserDAOImpl extends AbstractDAO<User> implements UserDAO {


    private static final String TABLE_NAME = "user";
    private static final String GET_ALL_QUERY = """
             SELECT u.* FROM user AS u
            """;
    private static final String GET_LIMIT_QUERY = GET_ALL_QUERY.concat(" LIMIT ?,?;");
    private static final String FIND_BY_ID_QUERY = GET_ALL_QUERY.concat(" WHERE u.id=?;");
    private static final String FIND_BY_EMAIL_AND_PASSWORD_QUERY = """
            SELECT u.* FROM user AS u WHERE u.email=? AND u.password=?;
            """;


    private static final String SAVE_QUERY =
            """
            INSERT INTO user (id, name, surname, password, email, phonenumber, balance, discount, role)
            VALUES (?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE
            name = VALUES(name),
            surname = VALUES(surname),
            password = VALUES(password),
            email = VALUES(email),
            phonenumber = VALUES(phonenumber),
            balance = VALUES(balance),
            discount = VALUES(discount),
            role = VALUES(role);
            """;


    public UserDAOImpl(Builder<User> builder, Connection connection) {
        super(builder, connection);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Optional<User> getById(Integer id) throws DAOException {
        return executeForSingleResult(FIND_BY_ID_QUERY, id);
    }

    @Override
    public List<User> getAll(int lim1, int lim2) throws DAOException {
        return executeQuery(GET_LIMIT_QUERY, lim1, lim2);
    }

    @Override
    public Optional<User> getByEmailAndPassword(String email, String password) throws DAOException {
        return executeForSingleResult(
                FIND_BY_EMAIL_AND_PASSWORD_QUERY,
                email, password);
    }

    @Override
    public void save(User entity) throws DAOException {
        Object [] objects = {
                entity.getId(),
                entity.getName(),
                entity.getSurname(),
                entity.getPassword(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getBalance(),
                entity.getDiscount(),
                entity.getRole()
        };
        executeUpdate(SAVE_QUERY, objects);
    }
}
