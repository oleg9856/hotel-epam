package org.hotel.command.impl.reservation;

import lombok.SneakyThrows;
import org.hotel.command.Command;
import org.hotel.command.CommandResult;
import org.hotel.entity.order.Reservation;
import org.hotel.entity.room.Room;
import org.hotel.entity.room.StatusRoom;
import org.hotel.exception.CommandException;
import org.hotel.exception.ServiceException;
import org.hotel.service.api.ReservationService;
import org.hotel.service.api.RoomService;
import org.hotel.utils.CurrentPageGetter;
import org.hotel.utils.RoomUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ApproveCommand extends AbstractReservationCommand implements Command {

    private static final String ROOM_ID_PARAMETER = "room_id";

    private RoomService roomService;
    private ReservationService reservationService;
    private RoomUtils roomUtils;

    public ApproveCommand(RoomService roomService, ReservationService reservationService, RoomUtils roomUtils) {
        super(reservationService);
        this.roomService = roomService;
        this.reservationService = reservationService;
        this.roomUtils = roomUtils;
    }

    @SneakyThrows
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        Reservation reservation = getReservation(request);
        int reservationId = reservation.getId();

        String roomIdParameter = request.getParameter(ROOM_ID_PARAMETER);
        int roomId = Integer.parseInt(roomIdParameter);
        Room room = roomService.getById(roomId)
                .orElseThrow(() -> new ServiceException("Room not found by id: " + roomId));
        validateRoom(room, reservation, request);
        BigDecimal totalPrice = getTotalPrice(reservation);

        reservationService.approve(reservationId, room, totalPrice);
        String page = CurrentPageGetter.getCurrentPage(request);
        return CommandResult.createRedirectCommandResult(page);
    }

    private void validateRoom(Room room, Reservation reservation, HttpServletRequest request) throws ServiceException {
        if (room.getStatus() != StatusRoom.FREE) {
            throw new ServiceException("Room is not active");
        }
        if (!roomUtils.isRoomSuitable(room, reservation)) {
            throw new ServiceException("Room is not suitable");
        }
        List<Reservation> reservations = reservationService.getAllReservations(
                getPageLimit(request));
        LocalDate arrivalDate = reservation.getStartDate();
        LocalDate departureDate = reservation.getEndDate();
        if (!roomUtils.isRoomFree(room, arrivalDate, departureDate, reservations)) {
            throw new ServiceException("Room is not free on the specified date");
        }
    }

}
