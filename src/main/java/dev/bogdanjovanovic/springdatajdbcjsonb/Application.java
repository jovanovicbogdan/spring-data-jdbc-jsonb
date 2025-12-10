package dev.bogdanjovanovic.springdatajdbcjsonb;

import dev.bogdanjovanovic.springdatajdbcjsonb.model.Address;
import dev.bogdanjovanovic.springdatajdbcjsonb.model.UserDetails;
import dev.bogdanjovanovic.springdatajdbcjsonb.model.UserEntity;
import dev.bogdanjovanovic.springdatajdbcjsonb.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import tools.jackson.databind.ObjectMapper;

@SpringBootApplication
public class Application {

  static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  CommandLineRunner runner(final UserRepository userRepository) {
    return _ -> {
      final var address1 = Address.builder()
          .city("Barcelona")
          .postalCode("9082")
          .street("Main street 2")
          .build();
      final var userDetails1 = UserDetails.builder()
          .address(address1)
          .birthDate(LocalDate.of(1990, 1, 1))
          .build();
      final var user1 = UserEntity.builder()
          .userId(null)
          .email("johndoe@email.com")
          .password("password")
          .userDetails(userDetails1)
          .build();

      final var address2 = Address.builder()
          .city("Berlin")
          .postalCode("4109")
          .street("Street 32")
          .build();
      final var userDetails2 = UserDetails.builder()
          .address(address2)
          .birthDate(LocalDate.of(1980, 4, 18))
          .build();
      final var user2 = UserEntity.builder()
          .userId(null)
          .email("janedoe@email.com")
          .password("password")
          .userDetails(userDetails2)
          .build();
      userRepository.saveAll(List.of(user1, user2));
      userRepository.findAll().forEach(System.out::println);
    };
  }

}
