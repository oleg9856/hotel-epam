package org.hotel.dao.api;

import org.hotel.entity.user.User;
import org.hotel.exception.DAOException;

import java.util.Optional;

public interface UserDAO extends DAO<User> {

    Optional<User> getByEmailAndPassword(String email, String password) throws DAOException;
}
