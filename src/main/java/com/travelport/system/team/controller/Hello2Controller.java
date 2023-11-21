package com.travelport.system.team.controller;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

@Controller("/hello2")
public class Hello2Controller {
  protected static final String RESPONSE = "Hello World (from2)";
  @Get
  @Produces(MediaType.TEXT_PLAIN)
  public String index() {
    return RESPONSE;
  }
}
