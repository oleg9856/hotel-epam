package org.hotel.command.impl.reservation.room;

import org.hotel.command.Command;
import org.hotel.command.CommandResult;
import org.hotel.command.impl.reservation.AbstractReservationCommand;
import org.hotel.entity.PageLimit;
import org.hotel.entity.room.Room;
import org.hotel.entity.room.RoomClass;
import org.hotel.entity.room.RoomSearch;
import org.hotel.entity.user.Role;
import org.hotel.entity.user.User;
import org.hotel.exception.CommandException;
import org.hotel.exception.ServiceException;
import org.hotel.service.api.ReservationService;
import org.hotel.service.api.RoomClassService;
import org.hotel.service.api.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import static org.hotel.constants.Constants.*;
import static org.hotel.constants.WebConstants.ROOMS_PAGE_JSP_PATH;
import static org.hotel.constants.WebConstants.VIEW_503_ERROR;

public class ShowRoomsCommand extends AbstractReservationCommand implements Command {

    private final RoomService roomService;
    private final RoomClassService roomClassService;

    public ShowRoomsCommand(ReservationService reservationService, RoomService roomService, RoomClassService roomClassService) {
        super(reservationService);
        this.roomService = roomService;
        this.roomClassService = roomClassService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        RoomSearch roomSearch = getRoomSearch(request);

        if(user.getRole() == Role.MANAGER){
            return getAdminRoomsCommand(request, roomSearch);
        }else {
            return getCustomerRoomsResult(request, roomSearch);
        }
    }

    private CommandResult getCustomerRoomsResult(HttpServletRequest request, RoomSearch roomSearch) {
        List<Room> rooms;
        try {
            rooms = roomService.getAllRooms(roomSearch);
        } catch (ServiceException e) {
            return CommandResult
                    .createForwardCommandResult(VIEW_503_ERROR);
        }
        request.setAttribute(ROOMS_ATTRIBUTE, rooms);
        return CommandResult
                .createForwardCommandResult(ROOMS_PAGE_JSP_PATH);
    }

    private CommandResult getAdminRoomsCommand(HttpServletRequest request, RoomSearch roomSearch) {
        List<Room> rooms;
        List<RoomClass> roomClasses;
        try {
            roomClasses = roomClassService.getAllRoomClasses(
                   getPageLimit(request)
            );
            rooms = roomService.getAllRooms(roomSearch);
            request.setAttribute(ROOM_CLASSES_ATTRIBUTE, roomClasses);
            request.setAttribute(ROOMS_ATTRIBUTE, rooms);
        } catch (ServiceException e) {
            return CommandResult
                    .createForwardCommandResult(VIEW_503_ERROR);
        }
        return CommandResult
                .createForwardCommandResult(ROOMS_PAGE_JSP_PATH);
    }

}
