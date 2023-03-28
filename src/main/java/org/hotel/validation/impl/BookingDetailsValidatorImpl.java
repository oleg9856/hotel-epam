package org.hotel.validation.impl;

import org.hotel.utils.DateUtils;
import org.hotel.validation.api.BookingDetailsValidator;

import java.time.LocalDate;

public class BookingDetailsValidatorImpl implements BookingDetailsValidator {

    private static final int MAXIMUM_ROOM_CAPACITY = 6;

    public BookingDetailsValidatorImpl() {
    }

    @Override
    public boolean isPeriodOfStayValid(LocalDate arrivalDate, LocalDate departureDate) {
        return !arrivalDate.isBefore(LocalDate.now()) && departureDate.isAfter(arrivalDate);
    }

    @Override
    public boolean isPersonsAmountValid(int personsAmount) {
        return personsAmount > 0 && personsAmount <= MAXIMUM_ROOM_CAPACITY;
    }

}
