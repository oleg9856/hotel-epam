package org.hotel.dao.impl;

import org.hotel.dao.api.ReservationDAO;
import org.hotel.entity.order.Reservation;
import org.hotel.exception.DAOException;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class ReservationDAOImplTest {

    private ReservationDAO reservationDAO;

    @Before
    public void setUp() throws Exception {
        reservationDAO = mock(ReservationDAOImpl.class);
    }

    @Test
    public void testSuccessGetAll() throws DAOException {
        when(reservationDAO.getAll(anyInt(), anyInt()))
                .thenReturn(List.of(new Reservation()));
        List<Reservation> all = reservationDAO.getAll(1, 2);

        assertEquals(List.of(new Reservation()), all);
    }

    @Test
    public void testSuccessGetByUserId() throws DAOException {
        when(reservationDAO.getByUserId(anyInt()))
                .thenReturn(List.of(new Reservation()));
        List<Reservation> all = reservationDAO.getByUserId(2);

        assertEquals(List.of(new Reservation()), all);
    }

    @Test
    public void testSuccessGetById() throws DAOException {
        when(reservationDAO.getById(anyInt()))
                .thenReturn(Optional.of(new Reservation()));
        Reservation reservation = reservationDAO.getById(3)
                .orElseThrow();
        assertEquals(new Reservation(), reservation);
    }

    @Test
    public void testSuccessSave() throws DAOException {
        doNothing().when(reservationDAO).save(new Reservation());
        reservationDAO.save(new Reservation());

        verify(reservationDAO, times(1)).save(new Reservation());
    }
}