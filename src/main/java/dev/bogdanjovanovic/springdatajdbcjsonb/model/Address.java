package dev.bogdanjovanovic.springdatajdbcjsonb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Address {

  private String city;
  private String postalCode;
  private String street;

}
