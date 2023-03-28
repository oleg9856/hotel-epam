package org.hotel.command.impl.reservation;

import org.hotel.command.Command;
import org.hotel.command.CommandResult;
import org.hotel.entity.room.RoomClass;
import org.hotel.entity.user.User;
import org.hotel.exception.CommandException;
import org.hotel.exception.ServiceException;
import org.hotel.service.api.ReservationService;
import org.hotel.service.api.RoomClassService;
import org.hotel.validation.api.BookingDetailsValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

import static org.hotel.constants.Constants.USER_ATTRIBUTE;
import static org.hotel.constants.WebConstants.VIEW_503_ERROR;

public class BookCommand implements Command {

    private static final CommandResult COMMAND_RESULT =
            CommandResult.createRedirectCommandResult("/controller?command=show_reservations_page");
    private static final String ARRIVAL_DATE_PARAMETER = "arrival_date";
    private static final String DEPARTURE_DATE_PARAMETER = "departure_date";
    private static final String ROOM_CLASS_PARAMETER = "room_class";
    private static final String PERSONS_AMOUNT_PARAMETER = "persons_amount";

    private final RoomClassService roomClassService;
    private final ReservationService reservationService;
    private final BookingDetailsValidator validator;

    public BookCommand(RoomClassService roomClassService, ReservationService reservationService,
                       BookingDetailsValidator validator) {
        this.roomClassService = roomClassService;
        this.reservationService = reservationService;
        this.validator = validator;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String arrivalDateParameter = request.getParameter(ARRIVAL_DATE_PARAMETER);
            LocalDate arrivalDate = LocalDate.parse(arrivalDateParameter);
            String departureDateParameter = request.getParameter(DEPARTURE_DATE_PARAMETER);
            LocalDate departureDate = LocalDate.parse(departureDateParameter);
            if (!validator.isPeriodOfStayValid(arrivalDate, departureDate)) {
                throw new CommandException("Invalid stay period: " + arrivalDate + ", " + departureDate);
            }

            String roomClassName = request.getParameter(ROOM_CLASS_PARAMETER);
            RoomClass roomClass = roomClassService.getByName(roomClassName)
                    .orElseThrow(() -> new ServiceException("Invalid room class: " + roomClassName));

            String personsAmountParameter = request.getParameter(PERSONS_AMOUNT_PARAMETER);
            int personsAmount = Integer.parseInt(personsAmountParameter);
            if (!validator.isPersonsAmountValid(personsAmount)) {
                throw new ServiceException("Invalid amount of persons: " + personsAmount);
            }

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER_ATTRIBUTE);
            reservationService.book(user, arrivalDate, departureDate, roomClass, personsAmount);
            return COMMAND_RESULT;
        } catch (ServiceException e) {
            return CommandResult.createForwardCommandResult(VIEW_503_ERROR);
        }
    }

}
