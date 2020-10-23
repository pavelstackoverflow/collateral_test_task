package com.mcb.creditfactory.service;

import com.mcb.creditfactory.dto.AirplaneDto;
import com.mcb.creditfactory.dto.AssessmentDto;
import com.mcb.creditfactory.dto.CarDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CarServiceTest {

    @Autowired
    CollateralService collateralService;

    @Test
    public void approvalTest() {
        CarDto carDto = new CarDto(
                1L,
                "bmw",
                "525i",
                Double.valueOf("100"),
                Short.valueOf("2015"),
                null
                );

        AirplaneDto airplaneDto = new AirplaneDto(
                1L,
                "airbus",
                "787",
                "1",
                700,
                500,
                Short.valueOf("2019"),
                null
        );

        Assertions.assertThat(collateralService.saveCollateral(airplaneDto)).isNull();
        Assertions.assertThat(collateralService.saveCollateral(carDto)).isNull();

        AssessmentDto assessmentDtoWrongCheck = new AssessmentDto(null,
                LocalDate.of(2016, Month.OCTOBER, 1), BigDecimal.valueOf(1000000));
        AssessmentDto assessmentDtoCorrect = new AssessmentDto(null,
                LocalDate.of(2019, Month.OCTOBER, 1), BigDecimal.valueOf(230000000));
        Set<AssessmentDto> assessmentDtos = new HashSet<>();

        assessmentDtos.add(assessmentDtoWrongCheck);

        carDto.setAssessmentDtos(assessmentDtos);
        airplaneDto.setAssessmentDtos(assessmentDtos);

        Assertions.assertThat(collateralService.saveCollateral(airplaneDto)).isNull();
        Assertions.assertThat(collateralService.saveCollateral(carDto)).isNull();

        assessmentDtos.add(assessmentDtoCorrect);

        Assertions.assertThat(collateralService.saveCollateral(carDto)).isNotNull().isEqualTo(1);
        Assertions.assertThat(collateralService.saveCollateral(airplaneDto)).isNotNull().isEqualTo(1);

        Assertions.assertThat(collateralService.getInfo(carDto)).isNotNull().hasFieldOrPropertyWithValue("model","525i");
        Assertions.assertThat(collateralService.getInfo(airplaneDto)).isNotNull().hasFieldOrPropertyWithValue("model","787");

    }


}
