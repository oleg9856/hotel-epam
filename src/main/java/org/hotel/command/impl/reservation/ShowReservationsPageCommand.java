package org.hotel.command.impl.reservation;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.hotel.command.Command;
import org.hotel.command.CommandResult;
import org.hotel.entity.order.Reservation;
import org.hotel.entity.order.ReservationStatus;
import org.hotel.entity.room.Room;
import org.hotel.entity.user.Role;
import org.hotel.entity.user.User;
import org.hotel.exception.CommandException;
import org.hotel.exception.ServiceException;
import org.hotel.service.api.ReservationService;
import org.hotel.service.api.RoomService;
import org.hotel.utils.RoomUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.hotel.constants.Constants.*;
import static org.hotel.constants.WebConstants.ROOM_PAGE_JSP_PATH;

@Log4j2
public class ShowReservationsPageCommand extends AbstractReservationCommand implements Command {

    private static final CommandResult COMMAND_RESULT =
            CommandResult
                    .createForwardCommandResult(ROOM_PAGE_JSP_PATH);

    private final ReservationService reservationService;
    private final RoomService roomService;
    private final RoomUtils roomUtils;

    public ShowReservationsPageCommand(ReservationService reservationService, RoomService roomService,
                                       RoomUtils roomUtils) {
        super(reservationService);
        this.reservationService = reservationService;
        this.roomService = roomService;
        this.roomUtils = roomUtils;
    }

    @SneakyThrows
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        log.trace("ShowReservationsPageCommand start...");
        User user = getUser(request);
        try {
            loadReservations(request, user);
        } catch (ServiceException e) {
            //todo: error page
            log.error("ShowReservationsPageCommand process failed --> {}",
                    e.getMessage());
            throw new CommandException(e);
        }

        if (shouldShowDetails(request)) {
            checkCredentials(request);
            Reservation reservation = getReservation(request);
            ReservationStatus reservationStatus = reservation.getReservationStatus();
            if (user.getRole() == Role.MANAGER && reservationStatus == ReservationStatus.WAITING) {
                processWaitingReservation(request, reservation);
            }
            request.setAttribute(RESERVATION_DETAILS_ATTRIBUTE, reservation);
        }
        log.trace("ShowReservationsPageCommand finished");
        return COMMAND_RESULT;
    }

    private void loadReservations(HttpServletRequest request, User user) throws ServiceException {
        List<Reservation> reservations;
        if (user.getRole() == Role.MANAGER) {
            reservations = reservationService
                    .getAllReservations(getPageLimit(request));
        } else {
            int id = user.getId();
            reservations = reservationService.getByUserId(id);
        }
        request.setAttribute(RESERVATIONS_ATTRIBUTE, reservations);
    }

    private boolean shouldShowDetails(HttpServletRequest request) {
        String detailsId = request.getParameter(DETAILS_ID_PARAMETER);
        return detailsId != null;
    }

    private void processWaitingReservation(HttpServletRequest request, Reservation reservation) throws ServiceException {
        reservation.setTotalPrice(getTotalPrice(reservation));

        List<Room> rooms = roomService.getAllRooms(getRoomSearch(request));
        List<Reservation> reservations = reservationService
                .getAllReservations(getPageLimit(request));
        List<Room> suitableRooms = roomUtils.getAvailableRooms(rooms, reservations, reservation);
        request.setAttribute(SUITABLE_ROOMS_ATTRIBUTE, suitableRooms);
    }


}
