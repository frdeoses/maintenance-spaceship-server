package com.fran.w2m.server.maintenance.spaceships.films.controllers;

import com.fran.w2m.server.maintenance.spaceships.films.exceptions.ValidationException;
import com.fran.w2m.server.maintenance.spaceships.films.models.User;
import com.fran.w2m.server.maintenance.spaceships.films.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService service;


    @GetMapping
    public List<User> list() {

        return service.findAll();

    }


    @PostMapping("/admin")
    @Operation(summary = "Crea un usuario con rol `admin`")
    public ResponseEntity<User> create(@RequestBody @Valid User user, BindingResult result) throws ValidationException { // BindingResult Tiene que estar al lado del objeeto a validar


        if (result.hasFieldErrors()) {
            validation(result);
        }

        user.setAdmin(true);

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));

    }

    @PostMapping("/register")
    @Operation(summary = "Crea un usuario con rol `user`")
    public ResponseEntity<User> register(@RequestBody @Valid User user, BindingResult result) throws ValidationException { // BindingResult Tiene que estar al lado del objeeto a validar

        if (result.hasFieldErrors()) {
            validation(result);
        }

        user.setAdmin(false);

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));

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
