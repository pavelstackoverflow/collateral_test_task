package com.mcb.creditfactory.service.airplane;

import com.mcb.creditfactory.dto.AirplaneDto;
import com.mcb.creditfactory.dto.AssessmentDto;
import com.mcb.creditfactory.external.ExternalApproveService;
import com.mcb.creditfactory.model.Airplane;
import com.mcb.creditfactory.model.AirplaneAssessment;
import com.mcb.creditfactory.repository.AirplaneAssessmentRepository;
import com.mcb.creditfactory.repository.AirplaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AirplaneServiceImpl implements AirplaneService {
    @Autowired
    private ExternalApproveService approveService;

    @Autowired
    private AirplaneRepository airplaneRepository;

    @Autowired
    private AirplaneAssessmentRepository airplaneAssessmentRepository;

    @Override
    public boolean approve(AirplaneDto dto) {
        return approveService.approve(new AirplaneAdapter(dto)) == 0;
    }

    @Override
    public Airplane save(Airplane airplane) {
        return airplaneRepository.save(airplane);
    }

    @Override
    public Optional<Airplane> load(Long id) {
        return airplaneRepository.getAirplaneById(id);
    }

    @Override
    public Airplane fromDto(AirplaneDto dto) {
        Set<AirplaneAssessment> airplaneAssessments = new HashSet<>();
        Airplane airplane = new Airplane(
                dto.getId(),
                dto.getBrand(),
                dto.getModel(),
                dto.getManufacturer(),
                dto.getFuelCapacity(),
                dto.getSeats(),
                dto.getYear(),
                airplaneAssessments);
        if (dto.getAssessmentDtos() != null) {
            for (AssessmentDto assessmentDto : dto.getAssessmentDtos()) {
                AirplaneAssessment airplaneAssessment = new AirplaneAssessment();
                airplaneAssessment.setId(assessmentDto.getId());
                airplaneAssessment.setValue(assessmentDto.getValue());
                airplaneAssessment.setDate(assessmentDto.getDate());
                airplaneAssessment.setAirplane(airplane);
                airplaneAssessments.add(airplaneAssessment);
            }
        }

        return airplane;
    }

    @Override
    public AirplaneDto toDTO(Airplane airplane) {

        Set<AssessmentDto> assessmentDtos = airplane.getAirplaneAssessments().stream()
                .map(airplaneAssessment -> {
                    AssessmentDto assessmentDto = new AssessmentDto();
                    assessmentDto.setId(airplaneAssessment.getId());
                    assessmentDto.setValue(airplaneAssessment.getValue());
                    assessmentDto.setDate(airplaneAssessment.getDate());
                    return assessmentDto;
                })
                .sorted(Comparator.comparing(AssessmentDto::getDate).reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return new AirplaneDto(
                airplane.getId(),
                airplane.getBrand(),
                airplane.getModel(),
                airplane.getManufacturer(),
                airplane.getFuelCapacity(),
                airplane.getSeats(),
                airplane.getYear(),
                assessmentDtos
        );
    }

    @Override
    public Long getId(Airplane airplane) {
        return airplane.getId();
    }
}
