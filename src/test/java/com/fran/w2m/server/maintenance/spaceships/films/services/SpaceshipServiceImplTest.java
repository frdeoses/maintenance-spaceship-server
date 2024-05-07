package com.fran.w2m.server.maintenance.spaceships.films.services;

import com.fran.w2m.server.maintenance.spaceships.films.converters.ConvertSpaceshipRQToSpaceship;
import com.fran.w2m.server.maintenance.spaceships.films.converters.ConvertSpaceshipToSpaceshipRS;
import com.fran.w2m.server.maintenance.spaceships.films.dto.SpaceshipRS;
import com.fran.w2m.server.maintenance.spaceships.films.exceptions.NotFoundSpaceshipException;
import com.fran.w2m.server.maintenance.spaceships.films.exceptions.SpaceshipModelException;
import com.fran.w2m.server.maintenance.spaceships.films.models.Spaceship;
import com.fran.w2m.server.maintenance.spaceships.films.repositories.SpaceshipRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.fran.w2m.server.maintenance.spaceships.films.utils.BeanUtils.ID;
import static com.fran.w2m.server.maintenance.spaceships.films.utils.BeanUtils.NAME;
import static com.fran.w2m.server.maintenance.spaceships.films.utils.BeanUtils.NAME_EDITED_EXPECTED;
import static com.fran.w2m.server.maintenance.spaceships.films.utils.BeanUtils.NAME_EXPECTED;
import static com.fran.w2m.server.maintenance.spaceships.films.utils.BeanUtils.PAGE_REQUEST;
import static com.fran.w2m.server.maintenance.spaceships.films.utils.BeanUtils.SPACESHIP_PAGE;
import static com.fran.w2m.server.maintenance.spaceships.films.utils.BeanUtils.SPACESHIP_RQ;
import static com.fran.w2m.server.maintenance.spaceships.films.utils.BeanUtils.SPACESHIP_RS;
import static com.fran.w2m.server.maintenance.spaceships.films.utils.BeanUtils.SPACESHIP_TEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SpaceshipServiceImplTest {

    @MockBean
    private SpaceshipRepository repository;

    private SpaceshipService service;

    @MockBean
    private ConvertSpaceshipToSpaceshipRS convertRS;

    @MockBean
    private ConvertSpaceshipRQToSpaceship convertRQ;

    @BeforeEach
    void setUp() {
        service = new SpaceshipServiceImpl(repository, convertRS, convertRQ);
    }


    @Test
    void whenFindAll_shouldReturnAllSpaceshipRS() {
        // Preparar datos de entrada y respuesta esperada

        when(repository.findAll(PAGE_REQUEST)).thenReturn(SPACESHIP_PAGE);
        when(convertRS.convert(SPACESHIP_TEST)).thenReturn(SPACESHIP_RS);

        Page<SpaceshipRS> result = service.findAll(PAGE_REQUEST);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(NAME_EXPECTED, result.getContent().getFirst().getName());
    }

    @Test
    void whenFindByIdWithId_shouldReturnSpaceshipRS() throws NotFoundSpaceshipException {
        when(repository.findById(ID)).thenReturn(Optional.of(SPACESHIP_TEST));

        when(convertRS.convert(SPACESHIP_TEST)).thenReturn(SPACESHIP_RS);

        SpaceshipRS result = service.findById(ID);

        assertNotNull(result);
        assertEquals(NAME_EXPECTED, result.getName());
    }

    @Test
    void whenFindByNameWithName_shouldReturnAllSpaceshipRS() {
        List<Spaceship> spaceships = Collections.singletonList(SPACESHIP_TEST);
        when(repository.findByNameContaining(NAME)).thenReturn(spaceships);
        when(convertRS.convert(SPACESHIP_TEST)).thenReturn(SPACESHIP_RS);

        List<SpaceshipRS> result = service.findByName(NAME);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(NAME_EXPECTED, result.getFirst().getName());
    }

    @Test
    void whenSaveWithSpaceshipRQ_shouldReturnSpaceshipRS() throws SpaceshipModelException {

        when(convertRQ.convert(SPACESHIP_RQ)).thenReturn(SPACESHIP_TEST);
        when(repository.save(SPACESHIP_TEST)).thenReturn(SPACESHIP_TEST);
        when(convertRS.convert(SPACESHIP_TEST)).thenReturn(SPACESHIP_RS);

        SpaceshipRS result = service.save(SPACESHIP_RQ);

        assertNotNull(result);
        assertEquals(NAME_EXPECTED, result.getName());
    }

    @Test
    void whenUpdateWithSpaceshipRQ_shouldReturnSpaceshipRS() throws NotFoundSpaceshipException, SpaceshipModelException {

        when(repository.findById(ID)).thenReturn(Optional.of(SPACESHIP_TEST));
        when(repository.save(SPACESHIP_TEST)).thenReturn(SPACESHIP_TEST);
        when(convertRQ.update(SPACESHIP_RQ, SPACESHIP_TEST)).thenReturn(SPACESHIP_TEST);
        when(convertRS.convert(SPACESHIP_TEST)).thenReturn(SPACESHIP_RS);

        SpaceshipRS result = service.update(SPACESHIP_RQ, ID);

        assertNotNull(result);
        assertEquals(NAME_EXPECTED, result.getName());
    }


    @Test
    void whenDeleteWithId_shouldReturnSpaceshipRS() throws NotFoundSpaceshipException {
        when(repository.findById(ID)).thenReturn(Optional.of(SPACESHIP_TEST));
        doNothing().when(repository).delete(SPACESHIP_TEST);
        when(convertRS.convert(SPACESHIP_TEST)).thenReturn(SPACESHIP_RS);

        SpaceshipRS result = service.delete(ID);

        assertNotNull(result);
        assertEquals(NAME_EXPECTED, result.getName());
    }


}