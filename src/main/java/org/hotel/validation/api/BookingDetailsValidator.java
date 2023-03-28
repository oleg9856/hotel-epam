package org.hotel.validation.api;

import java.time.LocalDate;
import java.util.Date;

public interface BookingDetailsValidator {

    boolean isPeriodOfStayValid(LocalDate arrivalDate, LocalDate departureDate);
    boolean isPersonsAmountValid(int personsAmount);

}
