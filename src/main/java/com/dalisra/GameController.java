package com.dalisra;

import com.dalisra.data.Warrior;
import com.dalisra.data.WarriorDTO;
import com.dalisra.repositories.WarriorRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import java.net.URI;
import java.util.*;

// if we remove path, it will default to root path "/"
@Controller
public class GameController {

    public final WarriorRepository warriorRepository;

    public GameController(WarriorRepository warriorRepository) {
        this.warriorRepository = warriorRepository;
    }

    // same here if we remove path it will default to root path "/"
    @Get
    /* Intro page that tells what to do in the game. */
    public Map<String, Object> index() {
        return Map.of(
            "Join us warrior", "/join/{name}",
            "List of warriors", "/warriors"
        );
    }

    @Get("/warriors")
    public Page<WarriorDTO> warriors(Pageable pageable){
        return warriorRepository.list(pageable);
    }

    @Get("/join/{name}")
    public HttpResponse<Warrior> join(String name){

        if(name == null || name.isEmpty() || Objects.equals(name, "{name}"))
            return HttpResponse.badRequest();

        String apiKey = UUID.randomUUID().toString();
        var warrior = new Warrior(null, apiKey, 0L, 1, name);
        warriorRepository.save(warrior);
        return HttpResponse.temporaryRedirect(URI.create("/warrior/" + apiKey));
    }
}