package org.hotel.entity.builder.impl;

import org.hotel.entity.room.Room;
import org.hotel.entity.user.Comment;
import org.hotel.entity.builder.Builder;
import org.hotel.entity.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CommentBuilder implements Builder<Comment> {

    private static final String COMMENT_ID_COLUMN = "id";
    private static final String USER_ID_COLUMN = "user_id";
    private static final String ROOM_ID_COLUMN = "room_id";
    private static final String DATE_OF_PUBLICATION = "date_of_publication";
    private static final String DESCRIPTION_COLUMN = "description";
    private final String idAlias;
    private static final Builder<Room> roomBuilder = new RoomBuilder(ROOM_ID_COLUMN);
    private static final Builder<User> userBuilder = new UserBuilder(USER_ID_COLUMN);


    public CommentBuilder() {
        this.idAlias = COMMENT_ID_COLUMN;
    }

    /**
     * Creates an instance of {@link CommentBuilder} with id alias.
     * This means, that id of {@link Comment} will be obtained from {@link ResultSet} using this alias.
     *
     * @param idAlias alias for id column
     */
    public CommentBuilder(String idAlias) {
        this.idAlias = idAlias;
    }

    @Override
    public Comment build(ResultSet resultSet) throws SQLException {
        int id = (int) resultSet.getObject(idAlias);
        if(resultSet.wasNull()) {
            return null;
        }
        User user = userBuilder.build(resultSet);
        Room room = roomBuilder.build(resultSet);
        LocalDate dOP = resultSet.getDate(DATE_OF_PUBLICATION).toLocalDate();
        String description = resultSet.getString(DESCRIPTION_COLUMN);
        return Comment.builder().commentId(id).user(user).room(room)
                .dateOfPublication(dOP).description(description).build();
    }
}
