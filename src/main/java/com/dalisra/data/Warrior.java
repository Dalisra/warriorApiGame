package com.dalisra.data;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

@MappedEntity
public record Warrior(
    @Id
    @GeneratedValue
    Long id,
    String apiKey,
    Long gold,
    Integer level,
    String name
) { }
