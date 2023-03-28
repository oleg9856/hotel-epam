package org.hotel.entity.builder.impl;

import org.hotel.entity.builder.Builder;
import org.hotel.entity.order.Reservation;
import org.hotel.entity.order.ReservationStatus;
import org.hotel.entity.room.Room;
import org.hotel.entity.user.User;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ReservationBuilder implements Builder<Reservation> {

    private static final String ORDER_ID_COLUMN = "id";
    private static final String USER_ID_COLUMN = "user_id";
    private static final String ROOM_ID_COLUMN = "room_id";
    private static final String TOTAL_PRICE_COLUMN = "total_price";
    private static final String ARRIVAL_DATE_COLUMN = "arrival_date";
    private static final String DEPARTURE_DATE_COLUMN = "departure_date";
    private static final String PRICE_AMOUNT_COLUMN = "person_amount";
    private static final String STATUS_COLUMN = "status";

    private final String idAlias;

    private final Builder<Room> roomBuilder = new RoomBuilder(ROOM_ID_COLUMN);
    private final Builder<User> userBuilder = new UserBuilder(USER_ID_COLUMN);

    public ReservationBuilder() {
        this.idAlias = ORDER_ID_COLUMN;
    }

    /**
     * Creates an instance of {@link ReservationBuilder} with id alias.
     * This means, that id of {@link Reservation} will be obtained from {@link ResultSet} using this alias.
     *
     * @param idAlias alias for id column
     */
    public ReservationBuilder(String idAlias) {
        this.idAlias = idAlias;
    }

    @Override
    public Reservation build(ResultSet resultSet) throws SQLException {
        int id = (int) resultSet.getObject(idAlias);
        if(resultSet.wasNull()) {
            return null;
        }
        User user = userBuilder.build(resultSet);
        Room room = roomBuilder.build(resultSet);
        BigDecimal price = resultSet.getBigDecimal(TOTAL_PRICE_COLUMN);
        LocalDate sd = resultSet.getDate(ARRIVAL_DATE_COLUMN).toLocalDate();
        LocalDate ed = resultSet.getDate(DEPARTURE_DATE_COLUMN).toLocalDate();
        Short p = resultSet.getShort(PRICE_AMOUNT_COLUMN);
        ReservationStatus status = ReservationStatus
                .valueOf(resultSet.getString(STATUS_COLUMN));

        return Reservation.builder().id(id).user(user).room(room)
                .totalPrice(price).startDate(sd).endDate(ed).reservationStatus(status)
                .personAmount(p).build();
    }

}
