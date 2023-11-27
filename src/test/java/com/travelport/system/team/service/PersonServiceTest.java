package com.travelport.system.team.service;

// import com.travelport.system.team.repository.PersonRepository;
// import io.micronaut.test.annotation.MockBean;
// import org.junit.jupiter.api.BeforeEach;

// import static org.junit.jupiter.api.Assertions.*;

import com.travelport.system.team.model.Person;
import com.travelport.system.team.repository.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PersonServiceTest {
  final PersonRepository mock = mock(PersonRepository.class);

  @Test
  @DisplayName("Finding an ID should call the repository")
  void testFindById() {
    final PersonService service = new PersonService(mock);
    service.findById("1");
    verify(mock).findById("1");
  }

  @Test
  @DisplayName("Retrieving a list of people should return a List")
  public void testFindAll() {

    final Person.PersonBuilder pb= Person.builder();
    final Collection<Person> peps = Set.of(
        pb.firstName("John").lastName("Doe").build(),
        pb.firstName("Jane").lastName("Smith").build());

    when(mock.findByLastNameIn(List.of("Doe", "Smith"))).thenReturn(peps);

    final PersonService service = new PersonService(mock);
    final Collection<Person> retVal = service.findByLastNameIn(List.of("Doe", "Smith"));
    verify(mock).findByLastNameIn(List.of("Doe", "Smith"));
    assertEquals(2, retVal.size());

    assertTrue(retVal.containsAll(peps));
  }

  @Test
  @DisplayName("Saving a Person with no ID should call the repository's save method")
  public void testSaveNewPerson() {
    final PersonService service = new PersonService(mock);
    final Person.PersonBuilder pb= Person.builder();
    final Person p = pb.firstName("John").lastName("Doe").build();
    service.save(p);
    verify(mock).save(p);
    verify(mock, times(0)).update(p);
  }

  @Test
  @DisplayName("Saving a Person with an ID should call the repository's update method")
  public void testSaveExistingPerson() {
    final PersonService service = new PersonService(mock);
    final Person.PersonBuilder pb= Person.builder();
    final Person p = pb.id("1").firstName("John").lastName("Doe").build();
    service.save(p);
    verify(mock).update(p);
    verify(mock, times(0)).save(p);
  }
}
