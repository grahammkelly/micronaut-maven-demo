package com.travelport.system.team.service;

import com.google.common.collect.Streams;
import com.travelport.system.team.model.Person;
import com.travelport.system.team.repository.PersonRepository;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class PersonService {
  private final PersonRepository personRepository;

  public PersonService(PersonRepository personRepository) {
    this.personRepository = personRepository;
  }

  public Optional<Person> findById(final String id) {
    return personRepository.findById(id);
  }

  public long count() {
    return personRepository.count();
  }

  public List<Person> findByLastNameIn(List<String> names) {
    return Streams.stream(personRepository.findByLastNameIn(names))
        .collect(Collectors.toList());
  }

  public List<Person> findAll() {
    return Streams.stream(personRepository.findAll())
        .collect(Collectors.toList());
  }

  public Person save(Person person) {
    return person.getId() == null ?
        personRepository.save(person) :
        personRepository.update(person);
  }

  public void delete(Person p) {
    personRepository.delete(p);
  }
}
