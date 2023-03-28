package org.hotel.utils;

import java.time.LocalDate;

public class DateUtils {

    public boolean isBetweenDates(LocalDate date, LocalDate start, LocalDate end) {
        return date.isAfter(start) && date.isBefore(end) || date.equals(start) || date.equals(end);
    }

}
