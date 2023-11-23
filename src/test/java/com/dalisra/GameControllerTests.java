package com.dalisra;

import com.dalisra.data.WarriorDTO;
import io.micronaut.data.model.Page;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
class GameControllerTests {

    @Inject
    EmbeddedApplication<?> application;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }

    @Client("/")
    public interface TestClient {

        @Get
        Map<String, Object> index();

        @Get("/warriors")
        Page<WarriorDTO> warriors();

        @Get("/join/{name}")
        WarriorDTO join(String name);
    }

    @Test
    void testJoinLinkForWarriors(TestClient testClient){
        var response = testClient.index();
        assertTrue(response.containsKey("Join us warrior"));
        assertEquals("/join/{name}", response.get("Join us warrior"));
    }

    @Test
    void testJoin(TestClient testClient){
        var warriorsInfo = testClient.warriors();
        assertEquals(0, warriorsInfo.getTotalSize());

        var warriorInfo = testClient.join("Thor");
        assertEquals("Thor", warriorInfo.name());
        assertEquals(1, warriorInfo.level());
        assertEquals(0, warriorInfo.gold());

        var warriorsInfoAfterInsert = testClient.warriors();

        assertEquals(1, warriorsInfoAfterInsert.getTotalSize());
    }

}
