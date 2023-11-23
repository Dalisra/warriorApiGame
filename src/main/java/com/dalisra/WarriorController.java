package com.dalisra;

import com.dalisra.data.WarriorDTO;
import com.dalisra.repositories.WarriorRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/warrior")
public class WarriorController {

    private final WarriorRepository warriorRepository;

    public WarriorController(WarriorRepository warriorRepository) {
        this.warriorRepository = warriorRepository;
    }

    @Get("/{apiKey}")
    public WarriorDTO info(String apiKey){
        return warriorRepository.findByApiKey(apiKey);
    }
}
