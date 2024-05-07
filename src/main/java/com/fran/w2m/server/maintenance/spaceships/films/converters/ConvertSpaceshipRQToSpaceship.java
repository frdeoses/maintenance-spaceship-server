package com.fran.w2m.server.maintenance.spaceships.films.converters;

import com.fran.w2m.server.maintenance.spaceships.films.dto.SpaceshipRQ;
import com.fran.w2m.server.maintenance.spaceships.films.models.Spaceship;
import org.springframework.stereotype.Component;

@Component
public class ConvertSpaceshipRQToSpaceship {

    public Spaceship convert(final SpaceshipRQ spaceship) {
        return new Spaceship(spaceship.getName(), spaceship.getModel(), spaceship.getDescription(), spaceship.getMaxSpeed());
    }

    public Spaceship update(final SpaceshipRQ spaceshipRQ, final Spaceship spaceship) {
        spaceship.setDescription(spaceshipRQ.getDescription());
        spaceship.setName(spaceshipRQ.getName());
        spaceship.setMaxSpeed(spaceshipRQ.getMaxSpeed());
        spaceship.setModel(spaceshipRQ.getModel());
        return spaceship;
    }


}
