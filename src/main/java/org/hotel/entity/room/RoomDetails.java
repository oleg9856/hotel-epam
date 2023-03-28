package org.hotel.entity.room;

import lombok.*;
import org.hotel.entity.Entity;

import java.sql.Blob;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDetails implements Entity {

  private Integer roomId;
  private String description;
  private String roomNumber;
}
