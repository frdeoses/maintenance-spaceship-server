package com.fran.w2m.server.maintenance.spaceships.films.services;

import com.fran.w2m.server.maintenance.spaceships.films.converters.ConvertSpaceshipRQToSpaceship;
import com.fran.w2m.server.maintenance.spaceships.films.converters.ConvertSpaceshipToSpaceshipRS;
import com.fran.w2m.server.maintenance.spaceships.films.dto.SpaceshipRQ;
import com.fran.w2m.server.maintenance.spaceships.films.dto.SpaceshipRS;
import com.fran.w2m.server.maintenance.spaceships.films.exceptions.NotFoundSpaceshipException;
import com.fran.w2m.server.maintenance.spaceships.films.exceptions.SpaceshipModelException;
import com.fran.w2m.server.maintenance.spaceships.films.models.Spaceship;
import com.fran.w2m.server.maintenance.spaceships.films.repositories.SpaceshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SpaceshipServiceImpl implements SpaceshipService {

    private final SpaceshipRepository repository;

    private final ConvertSpaceshipToSpaceshipRS convertRS;

    private final ConvertSpaceshipRQToSpaceship convertRQ;

    @Autowired
    public SpaceshipServiceImpl(final SpaceshipRepository repository,
                                final ConvertSpaceshipToSpaceshipRS convertRS,
                                final ConvertSpaceshipRQToSpaceship convertRQ) {
        this.repository = repository;
        this.convertRQ = convertRQ;
        this.convertRS = convertRS;
    }

    @Cacheable(value = "spaceshipPages", key = "#pageRequest.pageNumber + '-' + #pageRequest.pageSize")
    @Transactional(readOnly = true)
    @Override
    public Page<SpaceshipRS> findAll(PageRequest pageRequest) {
        Page<Spaceship> spaceshipPage = repository.findAll(pageRequest);
        return spaceshipPage.map(convertRS::convert);
    }


    @Cacheable(value = "spaceship", key = "#id")
    @Transactional(readOnly = true)
    @Override
    public SpaceshipRS findById(Long id) throws NotFoundSpaceshipException {

        Optional<Spaceship> optionalSpaceship = repository.findById(id);
        return convertRS.convert(optionalSpaceship
                .orElseThrow(() -> new NotFoundSpaceshipException("No se ha encontrado la nave en el sistema..."))
        );
    }

    @Cacheable(value = "spaceshipByName", key = "#name")
    @Transactional(readOnly = true)
    @Override
    public List<SpaceshipRS> findByName(String name) {
        List<Spaceship> spaceships = repository.findByNameContaining(name);
        return spaceships.stream().map(convertRS::convert).toList();
    }

    @CacheEvict(value = {"spaceship", "spaceshipPages", "spaceshipByName"}, allEntries = true)
    @Transactional
    @Override
    public SpaceshipRS save(SpaceshipRQ spaceship) throws SpaceshipModelException {

        if (repository.existsByModel(spaceship.getModel()))
            throw new SpaceshipModelException(String.format("Error: el modelo %s, ya se encuentra een el sistema....", spaceship.getModel()));

        return convertRS.convert(repository.save(convertRQ.convert(spaceship)));

    }


    @CachePut(value = "spaceship", key = "#id")
    @CacheEvict(value = {"spaceshipPages", "spaceshipByName"}, allEntries = true)
    @Transactional
    @Override
    public SpaceshipRS update(SpaceshipRQ spaceshipRQ, Long id) throws SpaceshipModelException, NotFoundSpaceshipException {

        Spaceship spaceship = repository.findById(id).orElseThrow(() -> new NotFoundSpaceshipException("No se ha encontrado la nave en el sistema..."));

        return convertRS.convert(repository.save(convertRQ.update(spaceshipRQ, spaceship)));
    }

    @CacheEvict(value = {"spaceship", "spaceshipPages", "spaceshipByName"}, key = "#id")
    @Transactional
    @Override
    public SpaceshipRS delete(Long id) throws NotFoundSpaceshipException {
        Optional<Spaceship> optionalSpaceship = repository.findById(id);

        Spaceship spaceship = optionalSpaceship.orElseThrow(() -> new NotFoundSpaceshipException("No se ha encontrado la nave en el sistema..."));
        repository.delete(spaceship);

        return convertRS.convert(spaceship);
    }
}
