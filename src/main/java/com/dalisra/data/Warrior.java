package com.dalisra.data;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.Index;
import io.micronaut.data.annotation.MappedEntity;

@MappedEntity
public record Warrior(
    @Id
    @GeneratedValue
    Long id,

    @Index(columns = {"apiKey"}, unique = true)
    String apiKey,

    Long gold,

    Integer level,

    @Index(columns = {"name"}, unique = true)
    String name

) { }
