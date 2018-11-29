/**
 * 
 */
package com.andrew.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import com.andrew.model.Person;

/**
 * Person repository for basic operations on Person entity.
 * @author andrew
 */
@RestResource(exported=false)
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {

}
