package org.hotel.command.impl.reservation.room;

import lombok.SneakyThrows;
import org.hotel.command.Command;
import org.hotel.command.CommandResult;
import org.hotel.entity.room.RoomClass;
import org.hotel.exception.CommandException;
import org.hotel.exception.ServiceException;
import org.hotel.service.api.RoomClassService;
import org.hotel.utils.CurrentPageGetter;
import org.hotel.validation.api.PriceValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hotel.constants.Constants.*;

public class SavePricesCommand implements Command {

    private final RoomClassService roomClassService;
    private final PriceValidator priceValidator;

    public SavePricesCommand(RoomClassService roomClassService, PriceValidator priceValidator) {
        this.roomClassService = roomClassService;
        this.priceValidator = priceValidator;
    }

    @SneakyThrows
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        List<RoomClass> roomClasses = getRoomClassesFromRequest(request);
        roomClassService.updatePrices(roomClasses);

        String currentPage = CurrentPageGetter.getCurrentPage(request);
        return CommandResult.createRedirectCommandResult(currentPage);
    }

    private List<RoomClass> getRoomClassesFromRequest(HttpServletRequest request) throws ServiceException {
        String[] nameParameters = request.getParameterValues(NAME_PARAMETER);
        String[] basicRateParameters = request.getParameterValues(BASIC_RATE_PARAMETER);
        String[] ratePerPersonParameters = request.getParameterValues(RATE_PER_PERSON_PARAMETER);
        int size = nameParameters.length;

        List<RoomClass> roomClasses = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            BigDecimal basicRate = new BigDecimal(basicRateParameters[i]);
            if (!priceValidator.isPriceValid(basicRate)) {
                throw new ServiceException("Invalid basic rate: " + basicRate);
            }
            BigDecimal ratePerPerson = new BigDecimal(ratePerPersonParameters[i]);
            if (!priceValidator.isPriceValid(ratePerPerson)) {
                throw new ServiceException("Invalid rate per person: " + ratePerPerson);
            }
            String name = nameParameters[i];
            RoomClass roomClass = RoomClass.builder().className(name)
                    .ratePerPerson(ratePerPerson).basicRate(basicRate).build();
            roomClasses.add(roomClass);
        }
        return roomClasses;
    }

}
