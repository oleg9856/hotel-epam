package org.hotel.command.impl.reservation;

import lombok.extern.log4j.Log4j2;
import org.hotel.command.Command;
import org.hotel.command.CommandResult;
import org.hotel.exception.CommandException;
import org.hotel.exception.ServiceException;
import org.hotel.service.api.ReservationService;
import org.hotel.utils.CurrentPageGetter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hotel.constants.WebConstants.ID_PARAMETER;
import static org.hotel.constants.WebConstants.VIEW_503_ERROR;

@Log4j2
public class SetCheckedInCommand implements Command {

    private final ReservationService reservationService;

    public SetCheckedInCommand(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        log.trace("SetCheckedOutCommand starts...");
        String idParameter = request.getParameter(ID_PARAMETER);
        int id = Integer.parseInt(idParameter);
        try {
            reservationService.setCheckedIn(id);
        } catch (ServiceException e) {
            log.trace("SetCheckedInCommand error ---> {}",
                    e.getMessage());
            return CommandResult
                    .createForwardCommandResult(VIEW_503_ERROR);
        }
        String page = CurrentPageGetter.getCurrentPage(request);
        log.trace("SetCheckedInCommand finish");
        return CommandResult.createRedirectCommandResult(page);
    }

}
