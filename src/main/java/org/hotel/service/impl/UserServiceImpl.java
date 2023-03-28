package org.hotel.service.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.hotel.dao.DAOHelper;
import org.hotel.dao.api.UserDAO;
import org.hotel.entity.builder.Builder;
import org.hotel.entity.builder.impl.UserBuilder;
import org.hotel.entity.user.Role;
import org.hotel.entity.user.User;
import org.hotel.exception.DAOException;
import org.hotel.exception.ServiceException;
import org.hotel.service.api.UserService;

import java.util.Optional;

@Log4j2
public class UserServiceImpl implements UserService {
    private final UserDAO dao;

    public UserServiceImpl(DAOHelper daoHelper) {
        Builder<User> builder = new UserBuilder();
        dao = daoHelper.userDao(builder);
    }

    @Override
    public Optional<User> getUserByEmailAndPassword(String email, String password) throws ServiceException {
        try {
            String encryptedPassword = DigestUtils.md5Hex(password);
            return dao.getByEmailAndPassword(email, encryptedPassword);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void saveUser(User user) throws ServiceException {
        try {
            String encryptedPassword = DigestUtils.md5Hex(user.getPassword());
            user.setPassword(
                    DigestUtils.md5Hex(encryptedPassword));
            dao.getByEmailAndPassword(user.getEmail(), user.getPassword())
                    .ifPresent((x) -> {
                        System.out.println("User already exists! --> {}"+ x.getEmail());
                        log.debug("User already exists! --> {}", x.getEmail());
                        throw new RuntimeException();
                    });
            dao.save(user);
        } catch (Exception e) {
            System.out.println("Error");
            log.debug("Error save entity ---> {}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void setRole(Integer id, Role role) throws ServiceException {
        try {
            User user = dao.getById(id)
                    .orElseThrow(() -> new ServiceException("Not found room by id"));
            user.setRole(role);
            dao.save(user);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
