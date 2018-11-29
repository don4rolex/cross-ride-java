/**
 * 
 */
package com.andrew.service;

import com.andrew.dto.RideDTO;
import com.andrew.dto.TopDriverDTO;
import com.andrew.model.Ride;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * RideService for rides.
 * @author andrew
 *
 */
public interface RideService {

  public Ride save(RideDTO rideDTO) throws ParseException;
  
  public Ride findById(Long rideId);

  public List<TopDriverDTO> findTopDrivers(LocalDateTime start, LocalDateTime end, Long total);
}
