package org.hotel.entity.builder.impl;

import lombok.extern.log4j.Log4j2;
import org.hotel.entity.builder.Builder;
import org.hotel.entity.user.Role;
import org.hotel.entity.user.User;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * An implementation of {@link Builder} for creating {@link User} objects.
 *
 * @see Builder
 * @see User
 */

@Log4j2
public class UserBuilder implements Builder<User> {

    private static final String DEFAULT_ID_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String SURNAME_COLUMN = "surname";
    private static final String PASSWORD_COLUMN = "password";
    private static final String EMAIL_COLUMN = "email";
    private static final String PHONE_NUMBER_COLUMN = "phonenumber";
    private static final String BALANCE_COLUMN = "balance";
    private static final String DISCOUNT_COLUMN = "discount";
    private static final String ROLE_COLUMN = "role";

    private final String idAlias;


    public UserBuilder() {
        idAlias = DEFAULT_ID_COLUMN;
    }

    /**
     * Creates an instance of {@link UserBuilder} with id alias.
     * This means, that id of {@link User} will be obtained from {@link ResultSet} using this alias.
     *
     * @param idAlias alias for id column
     */
    public UserBuilder(String idAlias) {
        this.idAlias = idAlias;
    }

    @Override
    public User build(ResultSet resultSet) throws SQLException {
        int id = (int) resultSet.getObject(idAlias);
        if (resultSet.wasNull()) {
            return null;
        }
        String name = resultSet.getString(NAME_COLUMN);
        String surname = resultSet.getString(SURNAME_COLUMN);
        String password = resultSet.getString(PASSWORD_COLUMN);
        String email = resultSet.getString(EMAIL_COLUMN);
        String phoneNumber = resultSet.getString(PHONE_NUMBER_COLUMN);
        BigDecimal balance = resultSet.getBigDecimal(BALANCE_COLUMN);
        BigDecimal discount = resultSet.getBigDecimal(DISCOUNT_COLUMN);
        Role role = Role.valueOf(resultSet.getString(ROLE_COLUMN));
        return User.builder().id(id).name(name).surname(surname).password(password)
                .email(email).phoneNumber(phoneNumber).balance(balance).discount(discount).role(role).build();
    }
}
