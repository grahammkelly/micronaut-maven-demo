package com.travelport.system.team.repository;

import com.travelport.system.team.model.Person;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@MongoRepository
public interface PersonRepository extends CrudRepository<Person, String> {
  Iterable<Person> findByLastNameIn(@NonNull List<String> names);
}
