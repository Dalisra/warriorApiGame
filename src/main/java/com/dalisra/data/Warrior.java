package com.dalisra.data;

import io.micronaut.data.annotation.*;

import java.time.LocalDate;

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
    String name,

    @DateUpdated LocalDate updated,
    @DateCreated LocalDate created,
    @Version Long version
) {

    public static final Long STARTING_GOLD = 100L;
    public static final int STARTING_LEVEL = 1;

    public Warrior(String name, String apiKey) {
        this(null, apiKey, STARTING_GOLD, STARTING_LEVEL, name, null, LocalDate.now(), 1L);
    }
}
