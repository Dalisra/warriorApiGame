package com.dalisra.data;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record WarriorDTO(
    Long id,
    Long gold,
    Integer level,
    String name
) { }
