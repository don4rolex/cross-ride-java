package com.andrew.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author andrew
 */
public class RideDTO implements Serializable {

  private static final long serialVersionUID = 9097639215351514002L;

  Long driverId;
  Long riderId;
  LocalDateTime startTime;
  LocalDateTime endTime;
  Double distance;

  public Long getDriverId() {
    return driverId;
  }

  public void setDriverId(Long driverId) {
    this.driverId = driverId;
  }

  public Long getRiderId() {
    return riderId;
  }

  public void setRiderId(Long riderId) {
    this.riderId = riderId;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalDateTime endTime) {
    this.endTime = endTime;
  }

  public Double getDistance() {
    return distance;
  }

  public void setDistance(Double distance) {
    this.distance = distance;
  }
}
