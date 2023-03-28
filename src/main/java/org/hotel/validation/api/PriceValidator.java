package org.hotel.validation.api;

import java.math.BigDecimal;

public interface PriceValidator {

    boolean isPriceValid(BigDecimal price);

}
