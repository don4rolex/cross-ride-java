/**
 * 
 */
package com.andrew.model;

import com.andrew.dto.TopDriverDTO;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ride")
@SqlResultSetMapping(
    name="topDriversMapping",
    classes={
        @ConstructorResult(
            targetClass= TopDriverDTO.class,
            columns={
                @ColumnResult(name="name"),
                @ColumnResult(name="email"),
                @ColumnResult(name="total_minutes", type = Long.class),
                @ColumnResult(name="max_duration", type = Long.class),
                @ColumnResult(name="avg_distance", type = Double.class),
            }
        )
    }
)
@NamedNativeQuery(name="findTopDrivers",
    query="SELECT p.name, p.email, SUM(TIMESTAMPDIFF(MINUTE, r.start_time, r.end_time)) AS total_minutes, " +
    "MAX(r.duration) AS max_duration, " +
    "AVG(r.distance) AS avg_distance FROM ride r, person p " +
    "WHERE r.start_time >= :startTime AND r.end_time <= :endTime " +
    "AND p.id = r.driver_id GROUP BY p.name, p.email LIMIT :total", resultSetMapping="topDriversMapping", resultClass = TopDriverDTO.class)
public class Ride implements Serializable{

  private static final long serialVersionUID = 9097639215351514001L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @NotNull
  @Column(name = "start_time")
  LocalDateTime startTime;
  
  @NotNull
  @Column(name = "end_time")
  LocalDateTime endTime;

  @NotNull
  @Column(name = "distance")
  Double distance;

  @NotNull
  @Column(name = "duration")
  Long duration;
  
  @ManyToOne
  @JoinColumn(name = "driver_id", referencedColumnName = "id")
  Person driver;
  
  @ManyToOne
  @JoinColumn(name = "rider_id", referencedColumnName = "id")
  Person rider;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Long getDuration() {
    return duration;
  }

  public void setDuration(Long duration) {
    this.duration = duration;
  }

  public Person getDriver() {
    return driver;
  }

  public void setDriver(Person driver) {
    this.driver = driver;
  }

  public Person getRider() {
    return rider;
  }

  public void setRider(Person rider) {
    this.rider = rider;
  }
  
  

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((distance == null) ? 0 : distance.hashCode());
    result = prime * result + ((driver == null) ? 0 : driver.hashCode());
    result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((rider == null) ? 0 : rider.hashCode());
    result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Ride other = (Ride) obj;
    if (distance == null) {
      if (other.distance != null)
        return false;
    } else if (!distance.equals(other.distance))
      return false;
    if (driver == null) {
      if (other.driver != null)
        return false;
    } else if (!driver.equals(other.driver))
      return false;
    if (endTime == null) {
      if (other.endTime != null)
        return false;
    } else if (!endTime.equals(other.endTime))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (rider == null) {
      if (other.rider != null)
        return false;
    } else if (!rider.equals(other.rider))
      return false;
    if (startTime == null) {
      if (other.startTime != null)
        return false;
    } else if (!startTime.equals(other.startTime))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Ride{" +
        "id=" + id +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", distance=" + distance +
        ", duration=" + duration +
        ", driver=" + driver +
        ", rider=" + rider +
        '}';
  }
}
