package org.hotel.command.impl;

import org.hotel.command.Command;
import org.hotel.command.CommandResult;
import org.hotel.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

import static org.hotel.constants.Constants.LOCALE_ATTRIBUTE;
import static org.hotel.constants.WebConstants.HOME_PATH;

public class LogoutCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        Locale locale = (Locale) session.getAttribute(LOCALE_ATTRIBUTE);
        session.invalidate();
        setLocale(request, locale);
        return CommandResult
                .createRedirectCommandResult(HOME_PATH);
    }

    private void setLocale(HttpServletRequest request, Locale locale) {
        HttpSession session = request.getSession();
        session.setAttribute(LOCALE_ATTRIBUTE, locale);
    }
}
