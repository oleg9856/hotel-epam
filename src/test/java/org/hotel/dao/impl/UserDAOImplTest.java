package org.hotel.dao.impl;

import org.hotel.dao.api.UserDAO;
import org.hotel.entity.user.User;
import org.hotel.exception.DAOException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class UserDAOImplTest {

    private UserDAO userDAO;

    @Before
    public void setUp() {
        userDAO = mock(UserDAOImpl.class);
    }

    @Test
    public void testSuccessGetById() throws DAOException {
        when(userDAO.getById(anyInt())).thenReturn(Optional.of(new User()));
        boolean isExists = userDAO.getById(1).isPresent();

        assertTrue(isExists);
    }

    @Test
    public void testSuccessGetAll() throws DAOException {
        when(userDAO.getAll(1, 1))
                .thenReturn(List.of(new User()));
        List<User> all = userDAO.getAll(1,1);
        assertEquals(List.of(new User()), all);
    }

    @Test
    public void testSuccessGetByEmailAndPassword() throws DAOException {
        when(userDAO.getByEmailAndPassword(anyString(), anyString())).thenReturn(Optional.of(new User()));
        User user = userDAO.getByEmailAndPassword(anyString(), anyString()).orElseThrow(DAOException::new);

        assertEquals(new User(), user);
    }

    @Test
    public void testSuccessSave() throws DAOException {
        doNothing().when(userDAO).save(new User());
        userDAO.save(new User());

        verify(userDAO, times(1)).save(new User());
    }
}