/**
 *
 */
package com.andrew.controller;

import com.andrew.repositories.PersonRepository;
import com.andrew.repositories.RideRepository;
import com.andrew.dto.TopDriverDTO;
import com.andrew.model.Person;
import com.andrew.model.Ride;
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
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RideControllerTest {

  MockMvc mockMvc;

  @Mock
  private RideController rideController;

  @Autowired
  private TestRestTemplate template;

  @Autowired
  RideRepository rideRepository;

  @Autowired
  PersonRepository personRepository;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(rideController).build();
  }

  @Test
  public void createNewRide() {
    ResponseEntity<Ride> response = newRide();
    Ride ride = response.getBody();

    Assert.assertEquals("Mark James", ride.getDriver().getName());
    Assert.assertEquals("John Doe", ride.getRider().getName());
    Assert.assertEquals(200, response.getStatusCode().value());

    rideRepository.deleteById(response.getBody().getId());
    personRepository.deleteById(response.getBody().getDriver().getId());
    personRepository.deleteById(response.getBody().getRider().getId());
  }

  @Test
  public void getTopDriver() {
    newRide();
    ResponseEntity<List<TopDriverDTO>> response = template.exchange(
        "/api/top-rides?startTime=2018-11-18T10:00:00&endTime=2018-11-18T11:00:00",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<TopDriverDTO>>() {});
    Assert.assertEquals(200, response.getStatusCode().value());
  }

  @Test
  public void getRideById() {
    Long id = newRide().getBody().getId();
    ResponseEntity<Ride> response = template.getForEntity("/api/ride/" + id, Ride.class);
    Ride ride = response.getBody();

    Assert.assertEquals("mark@yahoo.com", ride.getDriver().getEmail());
    Assert.assertEquals("john@yahoo.com", ride.getRider().getEmail());
    Assert.assertEquals(200, response.getStatusCode().value());

    rideRepository.deleteById(response.getBody().getId());
    personRepository.deleteById(response.getBody().getDriver().getId());
    personRepository.deleteById(response.getBody().getRider().getId());
  }

  @Test
  public void getRideById_InvalidId() {
    ResponseEntity<Ride> response = template.getForEntity("/api/ride/999", Ride.class);

    Ride Ride = response.getBody();

    Assert.assertNull(Ride);
    Assert.assertEquals(404, response.getStatusCode().value());
  }

  private ResponseEntity<Ride> newRide() {
    HttpEntity<Object> rider = getHttpEntity(
        "{\"name\":\"John Doe\",\"email\":\"john@yahoo.com\",\"registrationNumber\":\"A0001\"}");
    Long riderId = template.postForEntity("/api/person", rider, Person.class).getBody().getId();

    HttpEntity<Object> driver = getHttpEntity(
        "{\"name\":\"Mark James\",\"email\":\"mark@yahoo.com\",\"registrationNumber\":\"A0002\"}");
    Long driverId = template.postForEntity("/api/person", driver, Person.class).getBody().getId();

    HttpEntity<Object> ride = getHttpEntity(
        "{\"startTime\":\"2018-11-18T10:00:00\",\"endTime\":\"2018-11-18T10:30:00\",\"distance\":1000," +
            "\"driverId\":" + driverId + ",\"riderId\":" + riderId + "}");

    return template.postForEntity("/api/ride", ride, Ride.class);
  }

  private HttpEntity<Object> getHttpEntity(Object body) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    return new HttpEntity<Object>(body, headers);
  }

}
