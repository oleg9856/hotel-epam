package org.hotel.command.impl.reservation;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.hotel.command.Command;
import org.hotel.command.CommandResult;
import org.hotel.entity.order.Reservation;
import org.hotel.exception.CommandException;
import org.hotel.service.api.ReservationService;
import org.hotel.utils.CurrentPageGetter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
public class CancelReservationCommand extends AbstractReservationCommand implements Command {

    private final ReservationService reservationService;

    public CancelReservationCommand(ReservationService reservationService) {
        super(reservationService);
        this.reservationService = reservationService;
    }

    @SneakyThrows
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        log.trace("CancelReservationCommand starts...");
        checkCredentials(request);
        Reservation reservation = getReservation(request);
        int id = reservation.getId();
        reservationService.cancel(id);
        String page = CurrentPageGetter.getCurrentPage(request);
        log.trace("CancelReservationCommand finish");
        return CommandResult.createRedirectCommandResult(page);
    }

}
