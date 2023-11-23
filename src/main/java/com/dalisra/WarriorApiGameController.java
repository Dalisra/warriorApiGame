package com.dalisra;

import io.micronaut.http.annotation.*;

@Controller("/warriorApiGame")
public class WarriorApiGameController {

    @Get(uri="/", produces="text/plain")
    public String index() {
        return "Example Response";
    }
}