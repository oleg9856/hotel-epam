package org.hotel.command.impl;

import lombok.extern.log4j.Log4j2;
import org.hotel.command.Command;
import org.hotel.command.CommandResult;
import org.hotel.entity.user.User;
import org.hotel.exception.CommandException;
import org.hotel.exception.ServiceException;
import org.hotel.service.api.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hotel.constants.Constants.*;
import static org.hotel.constants.WebConstants.*;

@Log4j2
public class SignInCommand implements Command {

    private final UserService userService;

    public SignInCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        log.trace("Sign In command start...");
        User user = null;
        try {
            try {
                user = getUser(request);
            } catch (ServiceException e) {
                request.setAttribute(SIG_IN_INVALID_DATA, "Input correct data!");
                return CommandResult.createForwardCommandResult(SIG_IN_PAGE_JSP_PATH);
            }
            userService.saveUser(user);
        } catch (ServiceException e) {
            return CommandResult.createForwardCommandResult(SIG_IN_PAGE_JSP_PATH);
        }
        log.trace("Sig In command finish.");

        return CommandResult.createRedirectCommandResult(HOME_PATH);
    }

    private static User getUser(HttpServletRequest request) throws ServiceException {
        final String name = request.getParameter(NAME_PARAMETER);
        final String surname = request.getParameter(SURNAME_PARAMETER);
        if (name == null || surname == null){
            throw new ServiceException();
        }
        return User.builder().name(name).surname(surname)
                .password(request.getParameter(PASSWORD_PARAMETER))
                .email(request.getParameter(EMAIL_PARAMETER))
                .phoneNumber(request.getParameter(PHONE_NUMBER_PARAMETER))
                .balance(DEFAULT_SUM).discount(DEFAULT_SUM)
                .role(ROLE_USER_CUSTOMER).build();
    }
}
