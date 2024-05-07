package com.fran.w2m.server.maintenance.spaceships.films.repositories;

import com.fran.w2m.server.maintenance.spaceships.films.models.Spaceship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpaceshipRepository extends JpaRepository<Spaceship, Long> {

    boolean existsByModel(String model);

    @Query("select s from Spaceship s where s.name like %?1%")
    List<Spaceship> findByNameContaining(String name);
}
