package com.travelport.system.team.controller;

// import com.travelport.system.team.model.Person;
// import io.micronaut.http.HttpRequest;
// import io.micronaut.http.HttpStatus;
// import io.micronaut.http.client.HttpClient;
// import io.micronaut.http.client.annotation.Client;
// import io.micronaut.http.client.exceptions.HttpClientResponseException;
// import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
// import jakarta.inject.Inject;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import io.restassured.specification.RequestSpecification;

// import java.util.ArrayList;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertThrows;

//@MicronautTest
public class PersonControllerValidationTest {

//  @Inject
//  @Client("/")
//  HttpClient client;
//
//  @Test
//  @DisplayName("Test a GET works correctly through the client")
//  public void testGetThroughClientWorks() {
//    final Object a =
//        client.toBlocking().retrieve(HttpRequest.GET("/people"));
//    assertEquals(new ArrayList<>(), a);
////    assertEquals(HttpStatus.I_AM_A_TEAPOT, ex.getStatus());
//  }
//
//  @Test
//  @DisplayName("Test a GET works correctly through RestAssured")
//  public void testGetThroughRestAssuredWorks(final RequestSpecification spec) {
//    final List<Person> a =
//        spec.when().get("/people")
//        .then().statusCode(HttpStatus.OK.getCode())
//        .extract().body().as(List.class);
//    assertEquals(new ArrayList<>(), a);
//  }
//
//  @Test
//  @DisplayName("Test that the details of the incoming `Person` are validated")
//  void testStoredPersonIsValidated() {
////    final Person newPerson =
////        Person.builder().firstName("f").lastName("l").build();
////    personClient.toBlocking().exchange(
////        HttpRequest.POST("/people", newPerson));
//    final HttpClientResponseException ex = assertThrows(
//        HttpClientResponseException.class,
//        () -> personClient
//            .exchange(HttpRequest.POST("/people", new Person()), Person.class)
//    );
//
//    assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
//  }
//
//  @Test
//  @DisplayName("Test that makes sure attempts to add a person are validated correctly")
//  void testCannotAddIfPersonInvalid(final RequestSpecification spec) {
//    spec.when()
//          .body(new Person()).post("/people")
//        .then()
//          .statusCode(HttpStatus.BAD_REQUEST.getCode());
//  }
//
}
