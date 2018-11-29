/**
 * 
 */
package com.andrew.controller;

import com.andrew.repositories.PersonRepository;
import com.andrew.model.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

/**
 * @author kshah
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PersonControllerTest {
  
  MockMvc mockMvc;
  
  @Mock
  private PersonController personController;
  
  @Autowired
  private TestRestTemplate template;
  
  @Autowired
  PersonRepository personRepository;
  
  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
  }
  
  @Test
  public void register() {
    ResponseEntity<Person> response = registerPerson();
    //Delete this user
    personRepository.deleteById(response.getBody().getId());
    Assert.assertEquals("test 1", response.getBody().getName());
    Assert.assertEquals(200,response.getStatusCode().value());
  }

  @Test
  public void getAllPersons() {
    registerPerson();
    ResponseEntity<List<Person>> response = template.exchange(
        "/api/person",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<Person>>() {});
    Assert.assertEquals(1, response.getBody().size());

    Person person = response.getBody().get(0);
    //Delete this user
    personRepository.deleteById(person.getId());
    Assert.assertEquals("test 1", person.getName());
    Assert.assertEquals(200, response.getStatusCode().value());
  }

  @Test
  public void getPersonById() {
    Long id = registerPerson().getBody().getId();
    ResponseEntity<Person> response = template.getForEntity("/api/person/" + id, Person.class);

    Person person = response.getBody();
    //Delete this user
    personRepository.deleteById(person.getId());
    Assert.assertEquals("test 1", person.getName());
    Assert.assertEquals(200, response.getStatusCode().value());
  }

  @Test
  public void getPersonById_InvalidId() {
    ResponseEntity<Person> response = template.getForEntity("/api/person/999", Person.class);

    Person person = response.getBody();

    Assert.assertNull(person);
    Assert.assertEquals(404, response.getStatusCode().value());
  }

  private ResponseEntity<Person> registerPerson() {
    HttpEntity<Object> person = getHttpEntity(
        "{\"name\": \"test 1\", \"email\": \"test10000000000001@gmail.com\","
            + " \"registrationNumber\": \"41DCT\",\"registrationDate\":\"2018-08-08T12:12:12\" }");

    return template.postForEntity("/api/person", person, Person.class);
  }

  private HttpEntity<Object> getHttpEntity(Object body) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<Object>(body, headers);
  }

}
