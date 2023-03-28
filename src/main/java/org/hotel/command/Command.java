package org.hotel.command;

import org.hotel.exception.CommandException;
import org.hotel.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

    CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;

}
