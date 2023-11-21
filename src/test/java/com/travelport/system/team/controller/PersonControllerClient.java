package com.travelport.system.team.controller;

import com.travelport.system.team.model.Person;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Client("/people")
interface PersonControllerClient {
  @Get
  List<Person> getAll();

  @Get("/{id}")
  Person getById(@PathVariable("id") String id);

  @Get("/lastname")
  List<Person> getByLastName(@QueryValue @NotEmpty List<String> lastNames);

  @Post
  HttpResponse<Person> add(Person person);

  @Post("/{id}")
  HttpResponse<Person> update(@PathVariable("id") String id, Person person);

  @Delete("/{id}")
  HttpResponse<Person> delete(@PathVariable("id") String id);
}
