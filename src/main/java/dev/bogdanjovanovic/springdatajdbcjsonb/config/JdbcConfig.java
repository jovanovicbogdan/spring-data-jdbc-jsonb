package dev.bogdanjovanovic.springdatajdbcjsonb.config;

import dev.bogdanjovanovic.springdatajdbcjsonb.repository.converter.UserDetailsReadConverter;
import dev.bogdanjovanovic.springdatajdbcjsonb.repository.converter.UserDetailsWriteConverter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

@Configuration
@RequiredArgsConstructor
public class JdbcConfig extends AbstractJdbcConfiguration {

  private final UserDetailsWriteConverter userDetailsWriteConverter;
  private final UserDetailsReadConverter userDetailsReadConverter;

  @Override
  protected List<?> userConverters() {
    return List.of(userDetailsWriteConverter, userDetailsReadConverter);
  }

}
