package com.fran.w2m.server.maintenance.spaceships.films.validation;

import com.fran.w2m.server.maintenance.spaceships.films.services.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ExistByUsernameValidation implements ConstraintValidator<ExistByUsername, String> {

    @Autowired
    private UserService service;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(service)) return true;
        return !service.existsByUsername(username);
    }
}
