package dev.bogdanjovanovic.springdatajdbcjsonb.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserDetails {

  private Address address;
  private LocalDate birthDate;

}
