package com.travelport.system.team.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelport.system.team.model.Person;
import com.travelport.system.team.service.PersonService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MicronautTest(environments = "test")
public class PersonControllerClient {

  private static final TypeReference<List<Person>> LIST_OF_PEOPLE_TYPE = new TypeReference<>() {};

  @Inject @Client("/people")
  HttpClient client;

  @Inject PersonService personSvc;
  final PersonService mock = mock(PersonService.class);
  @MockBean(PersonService.class)
  PersonService personSvc() {
    return mock;
  }

  final ObjectMapper mapper = new ObjectMapper();

  @Test
  @DisplayName("Make sure a list of people is returned when people exist")
  public void testRetrieveAllHappypath() {
    final HttpRequest<?> request = HttpRequest.GET("/").accept(MediaType.APPLICATION_JSON);

    //Configure mock to return a list of people
    final List<Person> people = List.of(
        Person.builder().firstName("John").lastName("Doe").build(),
        Person.builder().firstName("Jane").lastName("Doe").build());
    when(mock.findAll()).thenReturn(people);

    try {
      final String bodyAsStr = client.toBlocking().retrieve(request);
      assertNotNull(bodyAsStr);

      final List<Person> body = mapper.readValue(bodyAsStr, LIST_OF_PEOPLE_TYPE);
      assertEquals(people, body);
    } catch (IOException ex) {
      fail("Caught exception in response body", ex);
    }
  }

  @Test
  @DisplayName("Make sure an empty list is returned when no people stored")
  public void testRetrieveAllNoPeople() {
    final HttpRequest<?> request = HttpRequest.GET("/").accept(MediaType.APPLICATION_JSON);

    //Configure mock to return an empty list of people
    when(mock.findAll()).thenReturn(Collections.EMPTY_LIST);

    try {
      final String bodyAsStr = client.toBlocking().retrieve(request);
      assertNotNull(bodyAsStr);

      final List<Object> body = mapper.readValue(bodyAsStr, List.class);
      assertEquals(Collections.EMPTY_LIST, body);
    } catch (IOException ex) {
      fail("Caught exception in response body", ex);
    }
  }

  @Test
  @DisplayName("Retrieve a person by id when the ID exists")
  public void testRetrieveByIdHappypath() {
    final HttpRequest<?> request = HttpRequest.GET("/123").accept(MediaType.APPLICATION_JSON);

    //Configure mock to return a list of people
    final Person p = Person.builder().id("123").firstName("John").lastName("Doe").build();
    when(mock.findById("123")).thenReturn(Optional.of(p));

    final Person body = client.toBlocking().retrieve(request, Person.class);
    assertNotNull(body);
    assertEquals(p, body);
  }

  @Test
  @DisplayName("Retrieve a person by id when the ID does not exist")
  public void testRetrieveByIdWNoMatch() {
    final HttpRequest<?> request = HttpRequest.GET("/123").accept(MediaType.APPLICATION_JSON);

    //Configure mock to return a list of people
    when(mock.findById("123")).thenReturn(Optional.empty());

    try {
      final Person body = client.toBlocking().retrieve(request, Person.class);
      fail("should have not found anything and returned a 404");
    } catch (HttpClientResponseException ex) {
      assertTrue(ex.getStatus().getCode() == 404, "should have returned a 404");
    }
  }

  @Test
  @DisplayName("Retrieve a person by last name when the last name exists")
  public void testRetrieveByLastNameHappypath() {
    final HttpRequest<?> request = HttpRequest.GET("/lastname?names=Doe,Smith").accept(MediaType.APPLICATION_JSON);

    //Configure mock to return a list of people
    final List<Person> people = List.of(
        Person.builder().firstName("John").lastName("Doe").age(28).build(),
        Person.builder().firstName("Jane").lastName("Doe").age(12).build(),
        Person.builder().firstName("Bob").lastName("Smith").age(59).build());
    when(mock.findByLastNameIn(any())).thenReturn(people);
    try {
      final String bodyAsStr = client.toBlocking().retrieve(request);
      assertNotNull(bodyAsStr);

      final List<Person> body = mapper.readValue(bodyAsStr, LIST_OF_PEOPLE_TYPE);

      //Verify MUST be after the invokation of the test!
      verify(mock).findByLastNameIn(argThat( arg -> {
        assertThat(arg.size(), equalTo(2));
        assertThat(arg, hasItems("Doe", "Smith"));
        return true;
      }));

      assertEquals(people, body);
    } catch (IOException ex) {
      fail("Caught exception in response body", ex);
    }
  }

  @Test
  @DisplayName("Retrieve a person by last name when matches")
  public void testRetrieveByLastNameNoMatch() {
    final HttpRequest<?> request = HttpRequest.GET("/lastname?names=Doe,Smith").accept(MediaType.APPLICATION_JSON);

    //Configure mock to return a list of people
    when(mock.findByLastNameIn(any())).thenReturn(Collections.EMPTY_LIST);

    try {
      final String bodyAsStr = client.toBlocking().retrieve(request);
      assertNotNull(bodyAsStr);

      final List<Person> body = mapper.readValue(bodyAsStr, LIST_OF_PEOPLE_TYPE);

      //Verify MUST be after the invokation of the test!
      verify(mock).findByLastNameIn(argThat( arg -> {
        assertThat(arg.size(), equalTo(2));
        assertThat(arg, hasItems("Doe", "Smith"));
        return true;
      }));

      assertEquals(Collections.EMPTY_LIST, body);
    } catch (IOException ex) {
      fail("Caught exception in response body", ex);
    }
  }

  @Disabled
  @Test
  @DisplayName("Add a person successfully - Created by Copilot")
  public void testAddPersonHappypath_createdByCopilot() {
    final Person.PersonBuilder pb = Person.builder().firstName("John").lastName("Doe").age(30);
    final Person in = pb.build();
    final Person expected = pb.id("1234").build();

    when(mock.save(in)).thenReturn(expected);

    final HttpRequest<Person> request = HttpRequest.POST("/", in)
        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

    final HttpResponse<Person> response = client.toBlocking().exchange(request, Person.class);
    assertEquals(HttpStatus.CREATED, response.getStatus());

    final Person body = response.body();
    assertNotNull(body);
    assertEquals(expected, body);
  }

  @Disabled("Working from command line. Looks like client creation is incorrect") //For now!
  @Test
  @DisplayName("Add a person successfully")
  public void testAddPersonHappypath() {
    final Person.PersonBuilder pb = Person.builder().firstName("John").lastName("Doe").age(30);
    final Person newPerson = pb.build();
    final Person expected = pb.id("1234").build();

    try {
      final HttpRequest<Person> request = HttpRequest.POST("/", newPerson);

    when(mock.save(newPerson)).thenReturn(expected);

      final HttpResponse<Person> response = client.toBlocking().exchange(request, Person.class);
      assertEquals(HttpStatus.CREATED, response.getStatus());

      final Person body = response.body();
      assertNotNull(body);
      assertEquals(expected, body);
    } catch (HttpClientResponseException ex) {
      fail(String.format("Received exception (%s) - Status %d", ex.getMessage(), ex.getStatus().getCode()));
    }
  }

  @Test
  @DisplayName("Test when adding, that the details of the incoming `Person` are validated")
  void testStoredPersonWithEmptyPerson() {
    final Person emptyPerson = new Person();
    final HttpClientResponseException ex = assertThrows(
        HttpClientResponseException.class,
        () -> client.toBlocking().exchange(HttpRequest.POST("/", emptyPerson), Person.class)
    );

    assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
  }

  @Disabled("Working from command line. Looks like client creation is incorrect") //For now!
  @Test
  @DisplayName("Updated a person where the ID exists")
  public void testUpdatePersonHappypath() {
    final Person.PersonBuilder pb = Person.builder().id("1234").firstName("John").lastName("Doe");
    final Person orig = pb.age(26).build();
    final Person update = pb.age(30).build();

    try {
      final HttpRequest<Person> request = HttpRequest.POST("/1234", update)
          .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

      when(mock.save(any())).thenReturn(orig);

      final HttpResponse<Person> response = client.toBlocking().exchange(request, Person.class);
      assertEquals(HttpStatus.ACCEPTED, response.getStatus());

      final Person body = response.body();
      assertNotNull(body);
      assertEquals(update, body);
    } catch (HttpClientResponseException ex) {
      fail(String.format("Received exception (%s) - Status %d", ex.getMessage(), ex.getStatus().getCode()));
    }

  }

  @Test
  @DisplayName("Delete a person where the ID exists")
  public void testDeleteHappyPath() {
    final HttpRequest<?> request = HttpRequest.DELETE("/1234").accept(MediaType.APPLICATION_JSON);

    //Configure mock to return a match
    final Person p = Person.builder().id("1234").firstName("John").lastName("Doe").build();
    when(mock.findById("1234")).thenReturn(Optional.of(p));

    final HttpResponse<Person> response = client.toBlocking().exchange(request, Person.class);
    verify(mock, times(1)).delete(p);

    assertEquals(HttpStatus.OK, response.getStatus());

    final Person body = response.body();
    assertNotNull(body);
    assertEquals(p, body);
  }

  @Test
  @DisplayName("Attempted delete when ID does not exist")
  public void testDeleteNoMatch() {
    final HttpRequest<?> request = HttpRequest.DELETE("/1234").accept(MediaType.APPLICATION_JSON);

    //Configure mock to return no match
    when(mock.findById("1234")).thenReturn(Optional.empty());

    try {
      final HttpResponse<Person> response = client.toBlocking().exchange(request, Person.class);
      fail("should have not found anything and returned a 404");
    } catch (HttpClientResponseException ex) {
      assertTrue(ex.getStatus() == HttpStatus.NOT_FOUND, "should have returned a 404");
    }
  }

}
