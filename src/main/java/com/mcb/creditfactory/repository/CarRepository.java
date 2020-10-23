package com.mcb.creditfactory.repository;

import com.mcb.creditfactory.model.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CarRepository extends CrudRepository<Car, Long> {

    @Query("select c from Car c " +
            "LEFT JOIN FETCH c.carAssessments " +
            "where c.id=?1")
    Optional<Car> getCarById(Long id);
}
