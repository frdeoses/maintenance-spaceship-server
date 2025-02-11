package com.fran.w2m.server.maintenance.spaceships.films.repositories;

import com.fran.w2m.server.maintenance.spaceships.films.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
