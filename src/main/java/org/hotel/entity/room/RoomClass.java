package org.hotel.entity.room;

import lombok.*;
import org.hotel.entity.Entity;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomClass implements Entity {

  private Integer classId;
  private String className;
  private BigDecimal basicRate;
  private BigDecimal ratePerPerson;
}
