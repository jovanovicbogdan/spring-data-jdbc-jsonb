package dev.bogdanjovanovic.springdatajdbcjsonb;

import dev.bogdanjovanovic.springdatajdbcjsonb.model.Address;
import dev.bogdanjovanovic.springdatajdbcjsonb.model.UserDetails;
import dev.bogdanjovanovic.springdatajdbcjsonb.model.UserEntity;
import dev.bogdanjovanovic.springdatajdbcjsonb.repository.UserRepository;
import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

  static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

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

}
