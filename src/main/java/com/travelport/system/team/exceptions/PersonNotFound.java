package com.travelport.system.team.exceptions;

import lombok.Getter;

@Getter
public class PersonNotFound extends Exception {
  @Getter
  private final String id;
  public PersonNotFound(String id) {
    super("Cannot find id '" + id + "'");
    this.id = id;
  }

  public PersonNotFound(String id, String additionalMsg) {
    super("Cannot find id '" + id + "' - " + additionalMsg);
    this.id = id;
  }
}
