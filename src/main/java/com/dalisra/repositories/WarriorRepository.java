package com.dalisra.repositories;

import com.dalisra.data.Warrior;
import com.dalisra.data.WarriorDTO;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.PageableRepository;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface WarriorRepository extends PageableRepository<Warrior, Long> {

    Page<WarriorDTO> list(Pageable pageable);

    WarriorDTO findByApiKey(String apiKey);
}
