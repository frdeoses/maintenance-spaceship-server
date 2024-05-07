package com.fran.w2m.server.maintenance.spaceships.films.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class Error {

    String message;

    String defaultMessage;

    int status;

    LocalDateTime date;
}
