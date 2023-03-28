package org.hotel.service.api;

import org.hotel.entity.user.Role;
import org.hotel.entity.user.User;
import org.hotel.exception.ServiceException;

import java.util.Optional;

public interface UserService {

    Optional<User> getUserByEmailAndPassword(String email, String password) throws ServiceException;
    void saveUser(User user) throws ServiceException;
    void setRole(Integer id, Role roleName) throws ServiceException;


}
