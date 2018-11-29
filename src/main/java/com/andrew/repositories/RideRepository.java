/**
 * 
 */
package com.andrew.repositories;

import com.andrew.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Person repository for basic operations on Ride entity.
 * @author andrew
 */
@RestResource(exported = false)
public interface RideRepository extends JpaRepository<Ride, Long> {

  @Query(nativeQuery = true, name = "findTopDrivers")
  public List findTopDrivers(@Param("startTime") LocalDateTime startTime,
                                           @Param("endTime") LocalDateTime endTime,
                                           @Param("total") Long total);
}
