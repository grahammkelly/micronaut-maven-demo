package com.travelport.system.team.handler;

import com.travelport.system.team.exceptions.PersonNotFound;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import jakarta.validation.ConstraintViolationException;

import java.util.Map;

@Singleton
@Requires(classes = {ConstraintViolationException.class, ExceptionHandler.class})
public class ValidationFailedHandler
    implements ExceptionHandler<ConstraintViolationException, HttpResponse<Map<String, Object>>> {
  @Produces(MediaType.APPLICATION_JSON)
  public HttpResponse<Map<String, Object>> handle(final HttpRequest req, final ConstraintViolationException ex) {
    return HttpResponse.badRequest(Map.of(
        "error", ex.getMessage()
        , "status", HttpStatus.PRECONDITION_FAILED.getCode()
        , "path", req.getPath()
        , "method", req.getMethodName()
    ));
  }
}
