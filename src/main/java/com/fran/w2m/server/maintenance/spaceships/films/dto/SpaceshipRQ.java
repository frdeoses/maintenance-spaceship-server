package com.fran.w2m.server.maintenance.spaceships.films.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SpaceshipRQ {

    @NotBlank
    String name;

    @NotBlank
    String model;

    String description;

    Integer maxSpeed;
}
