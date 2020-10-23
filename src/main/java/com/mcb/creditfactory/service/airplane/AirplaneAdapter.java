package com.mcb.creditfactory.service.airplane;

import com.mcb.creditfactory.dto.AirplaneDto;
import com.mcb.creditfactory.dto.AssessmentDto;
import com.mcb.creditfactory.external.CollateralObject;
import com.mcb.creditfactory.external.CollateralType;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;

@AllArgsConstructor
public class AirplaneAdapter implements CollateralObject {
    private AirplaneDto airplaneDto;

    @Override
    public BigDecimal getValue() {
        BigDecimal value = null;
        if (airplaneDto.getAssessmentDtos()!=null) {
            AssessmentDto assessmentDto = airplaneDto.getAssessmentDtos().stream()
                    .max(Comparator.comparing(AssessmentDto::getDate)).orElse(null);
            value = (assessmentDto != null && assessmentDto.getValue() != null) ? assessmentDto.getValue() : null;
        }
        return value;
    }

    @Override
    public Short getYear() {
        return airplaneDto.getYear();
    }

    @Override
    public LocalDate getDate() {
        LocalDate date = null;
        if (airplaneDto.getAssessmentDtos()!=null) {
            AssessmentDto assessmentDto = airplaneDto.getAssessmentDtos().stream()
                    .max(Comparator.comparing(AssessmentDto::getDate)).orElse(null);
            date = (assessmentDto != null && assessmentDto.getDate() != null) ? assessmentDto.getDate() : null;
        }
        return date;
    }

    @Override
    public CollateralType getType() {
        return CollateralType.AIRPLANE;
    }
}
