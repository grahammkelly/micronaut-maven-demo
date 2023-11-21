package com.travelport.system.team.handler;

// import io.micronaut.context.annotation.Requires;

import io.micronaut.core.exceptions.ExceptionHandler;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Status;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

// import java.util.Map;

@Singleton
@Slf4j
public class GeneralExceptionHandler implements ExceptionHandler<Throwable> {

  @Override
  @Status(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public void handle(final Throwable exception) {
    log.error("Received exception - {}", exception.getMessage(), exception);
//    return Map.of(
//        "error", exception.getMessage()
////        , "status", HttpStatus.INTERNAL_SERVER_ERROR.toString()
////        , "path", req.getPath()
////        , "method", req.getMethodName()
//    );
  }
}
