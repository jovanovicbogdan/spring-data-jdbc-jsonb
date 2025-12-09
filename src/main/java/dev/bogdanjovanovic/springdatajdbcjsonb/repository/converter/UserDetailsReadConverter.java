package dev.bogdanjovanovic.springdatajdbcjsonb.repository.converter;

import dev.bogdanjovanovic.springdatajdbcjsonb.model.UserDetails;
import lombok.RequiredArgsConstructor;
import org.postgresql.util.PGobject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Component
@ReadingConverter
@RequiredArgsConstructor
public class UserDetailsReadConverter implements Converter<PGobject, UserDetails> {

  private final ObjectMapper objectMapper;

  @Override
  public UserDetails convert(final PGobject source) {
    try {
      return objectMapper.readValue(source.getValue(), UserDetails.class);
    } catch (JacksonException ex) {
      throw new RuntimeException("Failed to deserialize UserDetails object", ex);
    }
  }

}
