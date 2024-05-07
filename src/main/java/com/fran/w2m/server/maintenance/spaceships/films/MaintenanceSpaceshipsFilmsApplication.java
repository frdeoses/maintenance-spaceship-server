package com.fran.w2m.server.maintenance.spaceships.films;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MaintenanceSpaceshipsFilmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaintenanceSpaceshipsFilmsApplication.class, args);
    }

}
