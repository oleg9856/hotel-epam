package org.hotel.command.impl.reservation.room;

import org.hotel.command.Command;
import org.hotel.command.CommandResult;
import org.hotel.entity.room.StatusRoom;
import org.hotel.exception.CommandException;
import org.hotel.exception.ServiceException;
import org.hotel.service.api.RoomService;
import org.hotel.utils.CurrentPageGetter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hotel.constants.Constants.STATUS_PARAMETER;
import static org.hotel.constants.WebConstants.ID_PARAMETER;

public class ChangeRoomStatusCommand implements Command {
    private final RoomService roomService;

    public ChangeRoomStatusCommand(RoomService roomService) {
        this.roomService = roomService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int id = Integer.parseInt(request.getParameter(ID_PARAMETER));
        String statusRoomParameter = request.getParameter(STATUS_PARAMETER);
        try {
            StatusRoom statusRoom = StatusRoom.valueOf(statusRoomParameter);
            roomService.setStatusRoom(id, statusRoom);
        } catch (ServiceException e) {
            request.setAttribute("fail_message","Try again");
        }

        String page = CurrentPageGetter.getCurrentPage(request);
        return CommandResult.createRedirectCommandResult(page);
    }


}
