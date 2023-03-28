package org.hotel.entity.builder.impl;

import org.hotel.entity.builder.Builder;
import org.hotel.entity.room.RoomClass;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomClassBuilder implements Builder<RoomClass> {

    private static final String DEFAULT_ID_COLUMN = "id";
    private static final String CLASS_NAME_COLUMN = "class_name";
    private static final String BASIC_RATE_COLUMN = "basic_rate";
    private static final String RATE_PER_PERSON_COLUMN = "rate_per_person";

    private final String idAlias;

    public RoomClassBuilder() {
        this.idAlias = DEFAULT_ID_COLUMN;
    }

    /**
     * Creates an instance of {@link RoomClassBuilder} with id alias.
     * This means, that id of {@link RoomClass} will be obtained from {@link ResultSet} using this alias.
     *
     * @param idAlias alias for id column
     */
    public RoomClassBuilder(String idAlias) {
        this.idAlias = idAlias;
    }

    @Override
    public RoomClass build(ResultSet resultSet) throws SQLException {
        Integer id = (Integer) resultSet.getObject(idAlias);
        if(resultSet.wasNull()) {
            return null;
        }
        String name = resultSet.getString(CLASS_NAME_COLUMN);
        BigDecimal rate = resultSet.getBigDecimal(BASIC_RATE_COLUMN);
        BigDecimal ratePerPerson = resultSet.getBigDecimal(RATE_PER_PERSON_COLUMN);
        return RoomClass.builder().classId(id).className(name).basicRate(rate)
                .ratePerPerson(ratePerPerson).build();
    }
}
