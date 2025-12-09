package dev.bogdanjovanovic.springdatajdbcjsonb.repository;

import dev.bogdanjovanovic.springdatajdbcjsonb.model.UserEntity;
import java.util.List;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<UserEntity, Long> {

  void saveAll(Iterable<UserEntity> users);

  List<UserEntity> findAll();

}
