package dev.bogdanjovanovic.springdatajdbcjsonb.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("users")
public class UserEntity {

  @Id
  private Long userId;
  private String email;
  private String password;
  /**
   * User details stored as JSONB.
   */
  private UserDetails userDetails;

}
