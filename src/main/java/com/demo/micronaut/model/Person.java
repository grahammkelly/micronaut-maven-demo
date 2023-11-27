package com.demo.micronaut.model;

import com.google.common.base.MoreObjects;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@MappedEntity
@Data
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
  @Id
  @GeneratedValue
  String id;

  @NotEmpty(message="First name cannot be empty")
  String firstName;

  @NotEmpty(message="Last name cannot be empty")
  String lastName;

  int age;

  String dob;

  String middleName;

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("id", id)
        .add("firstName", firstName)
        .add("lastName", lastName)
        .add("age", age)
        .add("dob", dob)
        .add("middleName", middleName)
        .omitNullValues()
        .toString();
  }

  public Person updateWith(Person p) {
    this.firstName = p.firstName;
    this.lastName = p.lastName;
    this.age = (p.age > 0 ? p.age : this.age);
    this.dob = (p.dob == null || p.dob.isEmpty() ? this.dob : p.dob);
    this.middleName = (p.middleName == null || p.middleName.isEmpty() ? this.middleName : p.middleName);
    return this;
  }
}
