package com.mcb.creditfactory.service.car;

import com.mcb.creditfactory.dto.AssessmentDto;
import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.external.CollateralObject;
import com.mcb.creditfactory.external.CollateralType;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;

@AllArgsConstructor
public class CarAdapter implements CollateralObject {
    private CarDto car;

    @Override
    public BigDecimal getValue() {
        BigDecimal value = null;
        if (car.getAssessmentDtos()!=null) {
            AssessmentDto assessmentDto = car.getAssessmentDtos().stream()
                    .max(Comparator.comparing(AssessmentDto::getDate)).orElse(null);
            value = (assessmentDto != null && assessmentDto.getValue() != null) ? assessmentDto.getValue() : null;
        }
        return value;
    }

    @Override
    public Short getYear() {
        return car.getYear();
    }

    @Override
    public LocalDate getDate() {
        LocalDate date = null;
        if (car.getAssessmentDtos()!=null) {
            AssessmentDto assessmentDto = car.getAssessmentDtos().stream()
                    .max(Comparator.comparing(AssessmentDto::getDate)).orElse(null);
            date = (assessmentDto != null && assessmentDto.getDate() != null) ? assessmentDto.getDate() : null;
        }
        return date;
    }

    @Override
    public CollateralType getType() {
        return CollateralType.CAR;
    }
}
