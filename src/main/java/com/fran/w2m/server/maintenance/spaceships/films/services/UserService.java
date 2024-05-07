package com.fran.w2m.server.maintenance.spaceships.films.services;

import com.fran.w2m.server.maintenance.spaceships.films.models.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User save(User user);

    boolean existsByUsername(String username);


}
