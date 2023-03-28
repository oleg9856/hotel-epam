package org.hotel.command.impl;

import lombok.extern.log4j.Log4j2;
import org.hotel.command.Command;
import org.hotel.command.CommandResult;
import org.hotel.exception.CommandException;
import org.hotel.exception.ServiceException;
import org.hotel.service.api.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.hotel.constants.Constants.*;
import static org.hotel.constants.WebConstants.*;

@Log4j2
public class LoginCommand implements Command {

    private final UserService userService;
    private static final CommandResult FORWARD_COMMAND_RESULT
            = CommandResult.createForwardCommandResult(LOGIN_PAGE_JSP_PATH);


    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        log.trace("Login command starts...");
        HttpSession session = request.getSession();
        final CommandResult[] PAGE_FORWARD = new CommandResult[1];

        String email = request.getParameter(EMAIL_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        try {
            if (email == null || password == null){
                throw new ServiceException();
            }
            userService
                    .getUserByEmailAndPassword(email,
                            password)
                    .ifPresentOrElse((user) -> {
                        session.setAttribute(USER_ATTRIBUTE, user);
                        PAGE_FORWARD[0] =
                                CommandResult.createRedirectCommandResult(LOGIN_PATH);
                    }, ()-> failedOperation(session, PAGE_FORWARD));
        } catch (ServiceException e) {
            PAGE_FORWARD[0] =
                    FORWARD_COMMAND_RESULT;
        }
        log.trace("Login command finished.");
        return PAGE_FORWARD[0];
    }

    private static void failedOperation(HttpSession session, CommandResult[] PAGE_FORWARD) {
        session.setAttribute(IS_LOGIN_FAILED_ATTRIBUTE, true);
        PAGE_FORWARD[0] = FORWARD_COMMAND_RESULT;
    }
}
