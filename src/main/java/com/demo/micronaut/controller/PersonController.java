package com.demo.micronaut.controller;

import com.demo.micronaut.exceptions.PersonNotFound;
import com.demo.micronaut.model.Person;
import com.demo.micronaut.service.PersonService;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.annotation.Status;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Controller("/people")
@ExecuteOn(TaskExecutors.IO)  //Make sure the controller is executed on a non-main thread pool so it does not block
@Slf4j
public class PersonController {

  private final PersonService personService;

  public PersonController(final PersonService personService) {
    this.personService = personService;
  }

  @Operation(summary = "Finds a `Person` based on the incoming ID")
  @Parameter(name = "id", description = "The ID of the `Person` to find", required = true)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The `Person` exists and is returned",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = Person.class))),
      @ApiResponse(responseCode = "404", description = "Unknown ID"),
      @ApiResponse(responseCode = "500", description = "Remote error, server failed")
  })
  @Tags(value = {
      @Tag(name = "find"),
      @Tag(name = "single"),
      @Tag(name = "byId")
  })
  @Get("/{id}")
  public Person findById(@PathVariable("id") final String id) throws PersonNotFound {
    final Optional<Person> found = personService.findById(id);
    found.ifPresentOrElse(
        person -> log.info("Looking for id '{}' - found {}", id, person),
        () -> log.info("Id '{}' does not exist", id)
    );
    return found.orElseThrow(() -> new PersonNotFound(id));
  }

  @Operation(summary = "Finds all `Person` entries")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = "A List (possibly empty) of stored people is returned. This list is empty if no people previously stored",
          content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Person.class)))
      ),
      @ApiResponse(responseCode = "500", description = "Remote error, server failed")
  })
  @Tags(value = {
      @Tag(name = "find"),
      @Tag(name = "list"),
      @Tag(name = "all")
  })
  @Get
  public List<Person> findAll() {
    final List<Person> people = personService.findAll();
    if (log.isDebugEnabled()) {
      log.debug("Found {} people - {}", people.size(), people);
    }
    return people;
  }

  @Operation(summary = "Gets a list of people with the given last names")
  @Parameter(name = "names", description = "a list of last names", required = true, in = ParameterIn.QUERY,
      content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(type = "string"))))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = "A List (possibly empty) of stored people is returned. This list is empty if no people matched the last names",
          content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Person.class)))
      ),
      @ApiResponse(responseCode = "500", description = "Remote error, server failed")
  })
  @Tags(value = {
      @Tag(name = "find"),
      @Tag(name = "list"),
      @Tag(name = "byLastname")
  })
  @Get("/lastname")
  public List<Person> findAllWithLastName(@QueryValue("names") @NotNull @NotEmpty final List<String> lastNames) {
    final List<Person> people = personService.findByLastNameIn(lastNames);
    if (log.isDebugEnabled()) {
      log.debug("Looking for [{}] - found {} entries: {}",
          String.join(", ", lastNames),
          people.size(), people);
    }
    return people;
  }

  @Operation(summary = "Adds a person to the database")
  @RequestBody(description = "The details of the `Person` to store", required = true,
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = Person.class)))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "The new `Person` was stored correctly",
          content = @Content(mediaType = "application/json",schema = @Schema(implementation=Person.class))
      ),
      @ApiResponse(responseCode = "500", description = "Remote error, server failed")
  })
  @Tags(value = {
      @Tag(name = "store"),
      @Tag(name = "save"),
      @Tag(name = "new"),
      @Tag(name = "create"),
      @Tag(name = "add")
  })
  @Post(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
  @Status(HttpStatus.CREATED)
  public Person addPerson(@Valid @NotNull final Person person) {
    final Person p = personService.save(person);
    if (log.isDebugEnabled()) {
      log.debug("Added {}, there are now {} people in the database", p, personService.count());
    }
    return p;
  }

  @Operation(summary = "Updates a person's details")
  @Parameter(name = "id", description = "The ID of the `Person` to update", required = true)
  @RequestBody(description = "The details of the updated `Person`. Note - If ID is passed in, this is ignored", required = true,
      content = @Content(mediaType = "application/json", schema = @Schema(implementation = Person.class)))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "202",
          description = "The details were updated correctly",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = Person.class))
      ),
      @ApiResponse(responseCode = "404"),
      @ApiResponse(responseCode = "500", description = "Remote error, server failed")
  })
  @Tags(value = {
      @Tag(name = "store"),
      @Tag(name = "save"),
      @Tag(name = "update"),
      @Tag(name = "byId")
  })
  @Post("/{id}")
  @Status(HttpStatus.ACCEPTED)
  public Person updatePerson(@PathVariable("id") final String id, @Valid @NotNull final Person person)
      throws PersonNotFound {
    final Person updated = personService.save(personService.findById(id)
        .orElseThrow(() -> new PersonNotFound(id, "unable to update"))
        .updateWith(person));
    if (log.isDebugEnabled()) {
      log.debug("Updated '{}' to {}", id, updated);
    }
    return updated;
  }

  @Operation(summary = "Removes the person with the given ID")
  @Parameter(name = "id", description = "The ID of the `Person` to remove", required = true)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "The `Person` exists and was removed",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = Person.class))),
      @ApiResponse(responseCode = "404", description = "Unknown ID"),
      @ApiResponse(responseCode = "500", description = "Remote error, server failed")
  })
  @Tags(value = {
      @Tag(name = "delete"),
      @Tag(name = "remove"),
      @Tag(name = "byId")
  })
  @Delete("/{id}")
  public Person delete(@PathVariable("id") final String id) throws PersonNotFound {
    final Person toDelete = personService.findById(id)
        .orElseThrow(() ->new PersonNotFound(id, "unable to delete"));
    personService.delete(toDelete);
    if (log.isDebugEnabled()) {
      log.debug("Deleted '{}'", toDelete);
    }
    return toDelete;
  }
}
