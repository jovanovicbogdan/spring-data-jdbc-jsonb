package dev.bogdanjovanovic.springdatajdbcjsonb.repository.converter;

import dev.bogdanjovanovic.springdatajdbcjsonb.model.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Component
@WritingConverter
@RequiredArgsConstructor
public class UserDetailsWriteConverter implements Converter<UserDetails, String> {

  private final ObjectMapper objectMapper;

  @Override
  public String convert(final UserDetails source) {
    try {
      return objectMapper.writeValueAsString(source);
    } catch (JacksonException ex) {
      throw new RuntimeException("Failed to serialize UserDetails object", ex);
    }
  }

}
