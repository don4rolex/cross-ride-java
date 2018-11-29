/**
 * 
 */
package com.andrew.service;

import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.andrew.repositories.PersonRepository;
import com.andrew.repositories.RideRepository;
import com.andrew.dto.RideDTO;
import com.andrew.dto.TopDriverDTO;
import com.andrew.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.andrew.model.Ride;

/**
 * @author andrew
 *
 */
@Service
public class RideServiceImpl implements RideService{

  @Autowired
  RideRepository rideRepository;

  @Autowired
  PersonRepository personRepository;

  public Ride save(RideDTO dto) throws ParseException {
    Long riderId = dto.getRiderId();
    if (riderId == null) {
      throw new IllegalArgumentException("No rider specified");
    }

    Long driverId = dto.getDriverId();
    if (driverId == null) {
      throw new IllegalArgumentException("No driver specified");
    }

    Person rider = personRepository.findById(riderId).orElse(null);
    if (rider == null) {
      throw new IllegalArgumentException("Rider isn't registered");
    }

    Person driver = personRepository.findById(driverId).orElse(null);
    if (driver == null) {
      throw new IllegalArgumentException("Driver isn't registered");
    }

    LocalDateTime start = dto.getStartTime();
    LocalDateTime end = dto.getEndTime();
    if (start.compareTo(end) >= 0) {
      throw new IllegalArgumentException("Invalid start date");
    }

    Duration duration = Duration.between(start, end);

    Ride ride = new Ride();
    ride.setRider(rider);
    ride.setDriver(driver);
    ride.setStartTime(start);
    ride.setEndTime(end);
    ride.setDistance(dto.getDistance());
    ride.setDuration(Math.abs(duration.toMinutes()));

    return rideRepository.save(ride);
  }
  
  public Ride findById(Long rideId) {
    Optional<Ride> optionalRide = rideRepository.findById(rideId);

    return optionalRide.orElse(null);
  }

  public List<TopDriverDTO> findTopDrivers(LocalDateTime start, LocalDateTime end, Long total) {
    List<TopDriverDTO> topRides = rideRepository.findTopDrivers(start, end, total);
    return topRides;
  }
}
