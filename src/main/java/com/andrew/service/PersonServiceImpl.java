/**
 * 
 */
package com.andrew.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.andrew.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.andrew.model.Person;

/**
 * @author andrew
 *
 */
@Service
public class PersonServiceImpl implements PersonService{

  @Autowired
  PersonRepository personRepository;

  /**
   * @see PersonService#getAll()
   */
  @Override
  public List<Person> getAll() {
    List<Person> personList = new ArrayList<>();
    personRepository.findAll().forEach(personList::add);

    return personList;
  }
  
  public Person save(Person p) {
    return personRepository.save(p);
  }

  @Override
  public Person findById(Long personId) {
    Optional<Person> dbPerson = personRepository.findById(personId);

    return dbPerson.orElse(null);
  }
  
  

}
