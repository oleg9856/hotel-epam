package org.hotel.validation.impl;

import org.hotel.validation.api.PaymentValidator;

import java.time.LocalDate;
public class PaymentValidatorImpl implements PaymentValidator {

    // card number
    private static final String CARD_NUMBER_REGEX = "\\d{16}";
    // expiration date
    private static final String EXPIRATION_DATE_REGEX = "(0[1-9]|1[0-2])/\\d{2}";
    // cvv number
    private static final String CVV_NUMBER_REGEX = "\\d{3}";

    public PaymentValidatorImpl() {

    }

    @Override
    public boolean isCardNumberValid(String cardNumber) {
        return cardNumber.matches(CARD_NUMBER_REGEX);
    }

    @Override
    public boolean isExpirationDateValid(String expirationDate) {
        if (!expirationDate.matches(EXPIRATION_DATE_REGEX)) {
            return false;
        }
        LocalDate cardExpirationDate = LocalDate.parse(expirationDate);
        return !cardExpirationDate.isBefore(LocalDate.now());
    }

    @Override
    public boolean isCvvNumberValid(String cvvNumber) {
        return cvvNumber.matches(CVV_NUMBER_REGEX);
    }

}
