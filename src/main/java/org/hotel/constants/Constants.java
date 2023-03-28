package org.hotel.constants;

import org.hotel.entity.user.Role;

import java.math.BigDecimal;

public class Constants {


    public static final String LOCALE_ATTRIBUTE = "locale";
    public static final String USER_ATTRIBUTE = "user";
    public static final String IS_LOGIN_FAILED_ATTRIBUTE = "is_login_failed";
    public static final String ROOM_CLASSES_ATTRIBUTE = "room_classes";
    public static final String ROOMS_ATTRIBUTE = "rooms";
    public static final String RESERVATIONS_ATTRIBUTE = "reservations";
    public static final String RESERVATION_DETAILS_ATTRIBUTE = "reservation_details";
    public static final String SUITABLE_ROOMS_ATTRIBUTE = "rooms";
    public static final String DETAILS_ID_PARAMETER = "id";
    public static final String CARD_NUMBER_PARAMETER = "card_number";
    public static final String VALID_THRU_PARAMETER = "valid_thru";
    public static final String CVV_NUMBER_PARAMETER = "cvv_number";
    public static final String BASIC_RATE_PARAMETER = "basic_rate";
    public static final String RATE_PER_PERSON_PARAMETER = "rate_per_person";
    public static final Role ROLE_USER_CUSTOMER = Role.CUSTOMER;
    public static final BigDecimal DEFAULT_SUM = new BigDecimal("0");
    public static final String NAME_PARAMETER = "name";
    public static final String SURNAME_PARAMETER = "surname";
    public static final String PASSWORD_PARAMETER = "password";
    public static final String EMAIL_PARAMETER = "email";
    public static final String PHONE_NUMBER_PARAMETER = "phone_number";
    public static final String STATUS_PARAMETER = "status_room";
    public static final String ORDER_BY_CRITERIA_PARAMETER = "order_by";
    public static final String CURRENT_PAGE_PARAMETER = "currentPage";
    public static final String ITEMS_PER_PAGE_PARAMETER = "itemsPerPage";
    public static final int CURRENT_PAGE_DEFAULT = 1;
    public static final int ITEMS_PER_PAGE_DEFAULT = 10;
    public static String SEARCH_DEFAULT = "price";

}
