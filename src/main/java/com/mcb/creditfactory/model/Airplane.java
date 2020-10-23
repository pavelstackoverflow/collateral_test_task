package com.mcb.creditfactory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

//@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AIRPLANE")
public class Airplane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private String manufacturer;
    @Column(name = "fuel_capacity")
    private Integer fuelCapacity;
    private Integer seats;

    @Column(name = "year_of_issue")
    private Short year;

    @OneToMany(mappedBy = "airplane", cascade = CascadeType.ALL)
    private Set<AirplaneAssessment> airplaneAssessments;

}

