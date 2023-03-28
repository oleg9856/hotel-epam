package org.hotel.entity.user;

import lombok.*;
import org.hotel.entity.Entity;

import javax.validation.constraints.Email;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Entity {

  private Integer id;
  private String name;
  private String surname;
  private String password;
  private String email;
  private String phoneNumber;
  private BigDecimal balance;
  private BigDecimal discount;
  private Role role;
}
