package com.demo.micronaut.handler;

import com.demo.micronaut.exceptions.PersonNotFound;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

import java.util.Map;

@Singleton
@Requires(classes = {PersonNotFound.class, ExceptionHandler.class})
public class PersonNotFoundHandler
    implements ExceptionHandler<PersonNotFound, HttpResponse<Map<String, Object>>> {

  @Produces(MediaType.APPLICATION_JSON)
  public HttpResponse<Map<String, Object>> handle(final HttpRequest req, final PersonNotFound ex) {
    return HttpResponse.notFound(Map.of(
        "error", ex.getMessage()
        , "status", HttpStatus.NOT_FOUND.getCode()
        , "path", req.getPath()
        , "method", req.getMethodName()
    ));
  }
}
