package com.fran.w2m.server.maintenance.spaceships.films.config;

import com.fran.w2m.server.maintenance.spaceships.films.exceptions.NotFoundSpaceshipException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Slf4j
public class SpaceshipLoggingAspect {

    @Before("execution(* com.fran.w2m.server.maintenance.spaceships.films.controllers.SpaceshipController.view(Long)) && args(id)")
    public void logBeforeRequestShip(Long id) throws NotFoundSpaceshipException {
        if (id < 0) {
            log.info("Se solicitó una nave con ID negativo: {}", id);
            throw new NotFoundSpaceshipException("Se solicitó una nave con ID negativo: " + id);
        }
    }
}
