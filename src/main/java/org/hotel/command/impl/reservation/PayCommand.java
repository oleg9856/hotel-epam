package org.hotel.command.impl.reservation;


import lombok.SneakyThrows;
import org.hotel.command.Command;
import org.hotel.command.CommandResult;
import org.hotel.entity.order.Reservation;
import org.hotel.exception.CommandException;
import org.hotel.service.api.ReservationService;
import org.hotel.utils.CurrentPageGetter;
import org.hotel.validation.api.PaymentValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.hotel.constants.Constants.*;

public class PayCommand extends AbstractReservationCommand implements Command {

    private final ReservationService reservationService;
    private final PaymentValidator validator;

    public PayCommand(ReservationService reservationService, PaymentValidator validator) {
        super(reservationService);
        this.reservationService = reservationService;
        this.validator = validator;
    }

    @SneakyThrows
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        checkCredentials(request);
        Reservation reservation = getReservation(request);
        int id = reservation.getId();
        String cardNumber = request.getParameter(CARD_NUMBER_PARAMETER);
        String expirationDate = request.getParameter(VALID_THRU_PARAMETER);
        String cvvNumber = request.getParameter(CVV_NUMBER_PARAMETER);
        if (!validator.isCardNumberValid(cardNumber)) {
            throw new CommandException("Invalid card number: " + cardNumber);
        }
        if (!validator.isExpirationDateValid(expirationDate)) {
            throw new CommandException("Invalid expiration date: " + expirationDate);
        }
        if (!validator.isCvvNumberValid(cvvNumber)) {
            throw new CommandException("Invalid cvv number: " + cvvNumber);
        }

        reservationService.setPaid(id);
        String page = CurrentPageGetter.getCurrentPage(request);
        return CommandResult.createRedirectCommandResult(page);
    }

}
