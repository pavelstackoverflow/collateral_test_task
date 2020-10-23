package com.mcb.creditfactory.repository;

import com.mcb.creditfactory.model.Airplane;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AirplaneRepository extends CrudRepository<Airplane, Long> {

    @Query("select a from Airplane a " +
            "LEFT JOIN FETCH a.airplaneAssessments " +
            "where a.id=?1")
    Optional<Airplane> getAirplaneById(Long id);
}
