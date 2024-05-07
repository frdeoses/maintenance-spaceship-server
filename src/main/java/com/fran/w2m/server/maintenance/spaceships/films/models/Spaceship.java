package com.fran.w2m.server.maintenance.spaceships.films.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "spaceships")
@Data
public class Spaceship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Size(min = 3, max = 20)
    private String name;

    @Column(name = "model", unique = true)
    private String model;

    @Column(name = "description")
    private String description;

    @Column(name = "max_speed")
    private Integer maxSpeed;

    public Spaceship() {
    }

    public Spaceship(final String name,
                     final String model,
                     final String description,
                     final Integer maxSpeed) {
        this.name = name;
        this.model = model;
        this.description = description;
        this.maxSpeed = maxSpeed;
    }


}
