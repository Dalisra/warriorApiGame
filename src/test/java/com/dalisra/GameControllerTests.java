package com.dalisra;

import com.dalisra.data.WarriorDTO;
import io.micronaut.context.annotation.Property;
import io.micronaut.core.util.StringUtils;
import io.micronaut.data.model.Page;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
class GameControllerTests {

    Logger log = LoggerFactory.getLogger(GameControllerTests.class);

    @Inject
    EmbeddedApplication<?> application;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }

    @Client("/")
    public interface GameControllerClient {

        @Get
        Map<String, Object> index();

        @Get("/warriors")
        Page<WarriorDTO> warriors();

        @Get("/join/{name}")
        HttpResponse join(String name);
    }

    @Client("/warrior")
    public interface WarriorClient {
        @Get("/{apiKey}")
        WarriorDTO info(String apiKey);
    }

    @Test
    void testIndex(GameControllerClient testClient){
        var response = testClient.index();
        assertTrue(response.containsKey("Join us warrior"));
        assertEquals("/join/{name}", response.get("Join us warrior"));
    }

    @Test
    @Property(name = "micronaut.http.client.follow-redirects", value = StringUtils.FALSE)
    void testJoin(GameControllerClient testClient, WarriorClient warriorClient){

        var warriorsPage = testClient.warriors();
        assertEquals(0, warriorsPage.getTotalSize());

        var warriorInfo = testClient.join("Thor");

        HttpClientResponseException exception = Assertions.assertThrows(HttpClientResponseException.class, () -> {
            testClient.join("Thor");
        });
        assertEquals(HttpStatus.CONFLICT, exception.getStatus());


        assertEquals(HttpStatus.TEMPORARY_REDIRECT, warriorInfo.getStatus());
        String location = warriorInfo.header("Location");
        assertTrue(location.startsWith("/warrior/"));
        String apiKey = location.substring("/warrior/".length());

        var warrior = warriorClient.info(apiKey);

        assertEquals("Thor", warrior.name());
        assertEquals(1, warrior.level());
        assertEquals(0, warrior.gold());

        var warriorsInfoAfterInsert = testClient.warriors();

        assertEquals(1, warriorsInfoAfterInsert.getTotalSize());
    }

}
