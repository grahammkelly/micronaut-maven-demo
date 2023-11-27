package com.travelport.system.team.handler;

import io.micronaut.http.server.exceptions.ExceptionHandler;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Status;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Singleton
@Slf4j
public class GeneralExceptionHandler
    implements ExceptionHandler<Throwable, HttpResponse<Map<String, Object>>> {

  @Override
  @Status(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public HttpResponse<Map<String, Object>> handle(final HttpRequest req, final Throwable exception) {
    log.error("Received exception - {}", exception.getMessage(), exception);
    return HttpResponse.serverError(Map.of(
        "error", exception.getMessage()
        , "status", HttpStatus.INTERNAL_SERVER_ERROR.toString()
        , "path", req.getPath()
        , "method", req.getMethodName()
    ));
  }
}
