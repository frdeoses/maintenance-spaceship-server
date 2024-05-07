package com.fran.w2m.server.maintenance.spaceships.films.services;

import com.fran.w2m.server.maintenance.spaceships.films.dto.SpaceshipRQ;
import com.fran.w2m.server.maintenance.spaceships.films.dto.SpaceshipRS;
import com.fran.w2m.server.maintenance.spaceships.films.exceptions.NotFoundSpaceshipException;
import com.fran.w2m.server.maintenance.spaceships.films.exceptions.SpaceshipModelException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface SpaceshipService {

    Page<SpaceshipRS> findAll(PageRequest pageRequest);

    SpaceshipRS findById(Long id) throws NotFoundSpaceshipException;

    List<SpaceshipRS> findByName(String name);

    SpaceshipRS save(SpaceshipRQ spaceship) throws SpaceshipModelException;

    SpaceshipRS update(SpaceshipRQ spaceship, Long id) throws SpaceshipModelException, NotFoundSpaceshipException;

    SpaceshipRS delete(Long id) throws NotFoundSpaceshipException;
}
