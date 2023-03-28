package org.hotel.entity.user;


import lombok.*;
import org.hotel.entity.Entity;
import org.hotel.entity.room.Room;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Entity {

  private Integer commentId;
  private User user;
  private Room room;
  private LocalDate dateOfPublication;
  private String description;
}


