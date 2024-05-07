package com.fran.w2m.server.maintenance.spaceships.films.repositories;

import com.fran.w2m.server.maintenance.spaceships.films.models.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
