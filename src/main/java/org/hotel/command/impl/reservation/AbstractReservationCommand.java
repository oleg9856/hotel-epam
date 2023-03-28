package org.hotel.command.impl.reservation;

import org.hotel.entity.PageLimit;
import org.hotel.entity.order.Reservation;
import org.hotel.entity.room.RoomSearch;
import org.hotel.entity.user.Role;
import org.hotel.entity.user.User;
import org.hotel.exception.ServiceException;
import org.hotel.service.api.ReservationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.time.Period;

import static org.hotel.constants.Constants.*;
import static org.hotel.constants.WebConstants.ID_PARAMETER;

public abstract class AbstractReservationCommand {

    private final ReservationService reservationService;

    protected AbstractReservationCommand(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    protected Reservation getReservation(HttpServletRequest request) throws ServiceException {
        String idParameter = request.getParameter(ID_PARAMETER);
        int id = Integer.parseInt(idParameter);
        return reservationService.getById(id)
                .orElseThrow(() -> new ServiceException("Invalid reservation id: " + id));
    }

    protected User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (User) session.getAttribute(USER_ATTRIBUTE);
    }

    protected void checkCredentials(HttpServletRequest request) throws ServiceException {
        User user = getUser(request);
        Reservation reservation = getReservation(request);
        User reservationUser = reservation.getUser();
        if (!user.getRole().equals(Role.MANAGER)
                && !user.equals(reservationUser)) {
            throw new ServiceException("User id: " + user.getId() +
                    " has no access to reservation id: " + reservation.getId());
        }
    }

    protected RoomSearch getRoomSearch(HttpServletRequest request) {
        String orderBy;
        int currentPage;
        int itemsPage;
        try {
            final String requestParameter = request.getParameter(ORDER_BY_CRITERIA_PARAMETER);
            if(requestParameter == null){
                throw new NumberFormatException();
            }
            orderBy = requestParameter;
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE_PARAMETER));
            itemsPage = Integer.parseInt(request.getParameter(ITEMS_PER_PAGE_PARAMETER));
        }catch (NumberFormatException e){
            orderBy = SEARCH_DEFAULT;
            currentPage = CURRENT_PAGE_DEFAULT;
            itemsPage = ITEMS_PER_PAGE_DEFAULT;
        }
        return RoomSearch.builder().orderBy(orderBy).currentPage(currentPage)
                .itemsPerPage(itemsPage).build();
    }

    protected PageLimit getPageLimit(HttpServletRequest request) {
        int currentPage;
        int itemsPage;
        try {
            currentPage = Integer.parseInt(request.getParameter(CURRENT_PAGE_PARAMETER));
            itemsPage = Integer.parseInt(request.getParameter(ITEMS_PER_PAGE_PARAMETER));
        }catch (NumberFormatException e){
            currentPage = CURRENT_PAGE_DEFAULT;
            itemsPage = ITEMS_PER_PAGE_DEFAULT;
        }
        return PageLimit.builder().currentPage(currentPage)
                .itemsPerPage(itemsPage).build();
    }

    protected static BigDecimal getTotalPrice(Reservation reservation) {
        BigDecimal totalPrice;
        BigDecimal numberPrice = reservation.getRoom().getNumberPrice();
        BigDecimal amountPerson = new BigDecimal(reservation.getPersonAmount());
        BigDecimal ratePerPerson =
                reservation.getRoom().getRoomClass().getRatePerPerson();

        int days = Period.between(reservation.getStartDate(), reservation.getEndDate())
                .getDays();

        totalPrice = numberPrice.add(ratePerPerson);
        totalPrice = totalPrice.multiply(amountPerson);
        totalPrice = totalPrice.multiply(new BigDecimal(days));
        return totalPrice;
    }

}
