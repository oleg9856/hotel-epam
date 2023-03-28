package org.hotel.validation.api;

public interface PaymentValidator {

    boolean isCardNumberValid(String cardNumber);
    boolean isExpirationDateValid(String expirationDate);
    boolean isCvvNumberValid(String cvvNumber);

}
