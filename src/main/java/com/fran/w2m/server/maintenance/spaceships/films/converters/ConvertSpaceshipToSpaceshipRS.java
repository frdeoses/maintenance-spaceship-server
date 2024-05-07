package com.fran.w2m.server.maintenance.spaceships.films.converters;

import com.fran.w2m.server.maintenance.spaceships.films.dto.SpaceshipRS;
import com.fran.w2m.server.maintenance.spaceships.films.models.Spaceship;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConvertSpaceshipToSpaceshipRS {

    public SpaceshipRS convert(Spaceship spaceship){
        return SpaceshipRS.builder()
                .description(spaceship.getDescription())
                .name(spaceship.getName())
                .maxSpeed(spaceship.getMaxSpeed())
                .model(spaceship.getModel())
                .build();
    }

    public List<SpaceshipRS> convertList(List<Spaceship> spaceships){
        return spaceships.stream()
                .map(this::convert)
                .toList();
    }
}
