package com.fran.w2m.server.maintenance.spaceships.films.repository;

import com.fran.w2m.server.maintenance.spaceships.films.models.Spaceship;
import com.fran.w2m.server.maintenance.spaceships.films.repositories.SpaceshipRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.fran.w2m.server.maintenance.spaceships.films.utils.BeanUtils.NAME;
import static com.fran.w2m.server.maintenance.spaceships.films.utils.BeanUtils.NAME_EXPECTED;
import static com.fran.w2m.server.maintenance.spaceships.films.utils.BeanUtils.SPACESHIP_TEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
class SpaceshipRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SpaceshipRepository repository;

    @Test
    void whenFindByNameWithName_shouldReturnAllSpaceshipRS() {
        // Crear y guardar una instancia en la base de datos en memoria
        ;
        entityManager.persist(SPACESHIP_TEST);
        entityManager.flush();

        // Ejecutar la consulta definida en el repositorio
        List<Spaceship> result = repository.findByNameContaining(NAME);

        // Verificar resultados
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(NAME_EXPECTED, result.getFirst().getName());
    }
}
