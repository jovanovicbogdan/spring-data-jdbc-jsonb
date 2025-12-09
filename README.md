## Storing Data as JSONB in PostgreSQL

Users table that contains `user_details JSONB` column

```sql
-- src/main/resources/schema.sql

CREATE TABLE IF NOT EXISTS users (
    user_id BIGSERIAL PRIMARY KEY,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    user_details JSONB NULL
);
```

User entity with `UserDetails` object column

```java
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
```

Define writing converter

```java
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
```

Define reading converter

```java
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
```

Register JDBC writing and reading converters

```java
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
```

## Usage

Example usage

```java
@Bean
CommandLineRunner runner(final UserRepository userRepository) {
  return _ -> {
    final var address = Address.builder()
        .city("Barcelona")
        .postalCode("9082")
        .street("Main street 2")
        .build();
    final var userDetails = UserDetails.builder()
        .address(address)
        .birthDate(LocalDate.of(1990, 1, 1))
        .build();
    final var user = UserEntity.builder()
        .userId(null)
        .email("johndoe@email.com")
        .password("password")
        .userDetails(userDetails)
        .build();
    userRepository.save(user);
    userRepository.findAll().forEach(System.out::println);
  };
}
```

## Run Locally

Start `postgresql` container and `adminer` web based database manager.

```shell
docker compose up -d
```

Access `adminer` at http://localhost:8000. Server is `db`, username is `postgres` and password is `rootpassword`.

Start the application `src/main/java/dev/bogdanjovanovic/springdatajdbcjsonb/Application.java`.

In the console will be printed saved database records

```
UserEntity(userId=1, email=johndoe@email.com, password=password, userDetails=UserDetails(address=Address(city=Barcelona, postalCode=9082, street=Main street 2), birthDate=1990-01-01))
UserEntity(userId=2, email=janedoe@email.com, password=password, userDetails=UserDetails(address=Address(city=Berlin, postalCode=4109, street=Street 32), birthDate=1980-04-18))
```

## Common Issues

If you are getting an error similar to the one below

```
Caused by: org.postgresql.util.PSQLException: ERROR: column "<column_name>" is of type jsonb but expression is of type character varying
```

Set the following hikari data source properties
```yaml
spring:
  datasource:
    hikari:
      data-source-properties:
        stringtype: unspecified
```
