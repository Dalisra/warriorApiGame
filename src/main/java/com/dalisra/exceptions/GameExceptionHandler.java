package com.dalisra.exceptions;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

import java.util.Map;

@Produces
@Singleton
@Requires(classes = {GameException.class, ExceptionHandler.class})
public class GameExceptionHandler implements ExceptionHandler<GameException, HttpResponse<Map<String, String>>> {

    @Override
    public HttpResponse<Map<String, String>> handle(HttpRequest request, GameException exception) {
        return HttpResponse.status(exception.getHttpStatus()).body(
            Map.of(
                "error", exception.getMessage(),
                "reason", exception.getHttpStatus().getReason()
            )
        );
    }
}
