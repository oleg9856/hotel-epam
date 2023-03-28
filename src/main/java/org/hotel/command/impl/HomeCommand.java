package org.hotel.command.impl;

import lombok.extern.log4j.Log4j2;
import org.hotel.command.Command;
import org.hotel.command.CommandResult;
import org.hotel.exception.CommandException;
import org.hotel.service.api.RoomService;
import org.hotel.service.api.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hotel.constants.WebConstants.HOME_PAGE_JSP_PATH;

@Log4j2
public class HomeCommand implements Command {

    public HomeCommand() {
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        return CommandResult.createForwardCommandResult(HOME_PAGE_JSP_PATH);
    }
}
