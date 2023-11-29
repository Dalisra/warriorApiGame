package com.dalisra.data;

import io.micronaut.serde.annotation.Serdeable;

import java.time.LocalDate;

@Serdeable
public record WarriorDTO(
    Long id,
    Long gold,
    Integer level,
    String name,
    LocalDate updated
) { }
