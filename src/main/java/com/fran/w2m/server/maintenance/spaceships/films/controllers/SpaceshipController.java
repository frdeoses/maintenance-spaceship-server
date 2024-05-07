package com.fran.w2m.server.maintenance.spaceships.films.controllers;

import com.fran.w2m.server.maintenance.spaceships.films.dto.SpaceshipRQ;
import com.fran.w2m.server.maintenance.spaceships.films.dto.SpaceshipRS;
import com.fran.w2m.server.maintenance.spaceships.films.exceptions.NotFoundSpaceshipException;
import com.fran.w2m.server.maintenance.spaceships.films.exceptions.SpaceshipModelException;
import com.fran.w2m.server.maintenance.spaceships.films.exceptions.ValidationException;
import com.fran.w2m.server.maintenance.spaceships.films.services.SpaceshipService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/spaceships")
public class SpaceshipController {

    private final SpaceshipService service;

    public SpaceshipController(SpaceshipService service) {
        this.service = service;
    }

    @Operation(summary = "Obtener una lista paginada de naves espaciales")
    @GetMapping
    public Page<SpaceshipRS> list(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {

        return service.findAll(PageRequest.of(page, size));

    }

    @Operation(summary = "Obtener una nave espacial por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<SpaceshipRS> view(@PathVariable Long id) throws NotFoundSpaceshipException {

        return ResponseEntity.ok().body(service.findById(id));

    }

    @Operation(summary = "Buscar naves espaciales por nombre")
    @GetMapping("/search")
    public ResponseEntity<List<SpaceshipRS>> findByName(@RequestParam String name) {

        return ResponseEntity.ok().body(service.findByName(name));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear una nueva nave espacial")
    @PostMapping
    public ResponseEntity<SpaceshipRS> create(@RequestBody @Valid SpaceshipRQ spaceship, BindingResult result) throws SpaceshipModelException, ValidationException { // BindingResult Tiene que estar al lado del objeeto a validar

        if (result.hasFieldErrors()) {
            validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(spaceship));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar una nave espacial existente")
    @PutMapping("/{id}")
    public ResponseEntity<SpaceshipRS> update(@RequestBody @Valid SpaceshipRQ spaceship, BindingResult result, @PathVariable Long id) throws SpaceshipModelException, NotFoundSpaceshipException, ValidationException {

        if (result.hasFieldErrors()) {
            validation(result);
        }

        return ResponseEntity.ok().body(service.update(spaceship, id));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Eliminar una nave espacial por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<SpaceshipRS> delete(@PathVariable Long id) throws NotFoundSpaceshipException {

        return ResponseEntity.ok().body(service.delete(id));

    }

    private void validation(BindingResult result) throws ValidationException {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(fieldError ->
                errors.put(fieldError.getField(), "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
        );

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

    }
}
