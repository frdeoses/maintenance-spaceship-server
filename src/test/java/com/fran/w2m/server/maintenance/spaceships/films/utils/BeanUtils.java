package com.fran.w2m.server.maintenance.spaceships.films.utils;

import com.fran.w2m.server.maintenance.spaceships.films.dto.SpaceshipRQ;
import com.fran.w2m.server.maintenance.spaceships.films.dto.SpaceshipRS;
import com.fran.w2m.server.maintenance.spaceships.films.models.Spaceship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

public class BeanUtils {

    public static final int PAGE_NUMBER = 0;

    public static final int PAGE_SIZE = 10;

    public static final Long ID = 1L;

    public static final String NAME_EXPECTED = "test1";

    public static final String NAME = "test1";

    public static final String MODEL = "V-test";
    public static final String DESCRIPTION = "test description";
    public static final int MAX_SPEED = 500;
    public static Spaceship SPACESHIP_TEST = new Spaceship("test1", MODEL, DESCRIPTION, MAX_SPEED);

    public static Page<Spaceship> SPACESHIP_PAGE = new PageImpl<>(Collections.singletonList(SPACESHIP_TEST));

    public static SpaceshipRS SPACESHIP_RS = SpaceshipRS.builder()
            .name(NAME)
            .model(MODEL)
            .description(DESCRIPTION)
            .maxSpeed(MAX_SPEED)
            .build();

    public static Page<SpaceshipRS> SPACESHIP_RSPAGE = new PageImpl<>(Collections.singletonList(SPACESHIP_RS));

    public static PageRequest PAGE_REQUEST = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);;

    public static SpaceshipRQ SPACESHIP_RQ = SpaceshipRQ.builder()
            .name(NAME)
            .model(MODEL)
            .description(DESCRIPTION)
            .maxSpeed(MAX_SPEED)
            .build();

    public static final String NAME_EDITED_EXPECTED = "test1-edit";
}
