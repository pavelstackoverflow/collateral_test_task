package com.mcb.creditfactory.service.car;

import com.mcb.creditfactory.dto.AssessmentDto;
import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.external.ExternalApproveService;
import com.mcb.creditfactory.model.Car;
import com.mcb.creditfactory.model.CarAssessment;
import com.mcb.creditfactory.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private ExternalApproveService approveService;

    @Autowired
    private CarRepository carRepository;

    @Override
    public boolean approve(CarDto dto) {
        return approveService.approve(new CarAdapter(dto)) == 0;
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Optional<Car> load(Long id) {
        return carRepository.getCarById(id);
    }

    @Override
    public Car fromDto(CarDto dto) {
        Set<CarAssessment> carAssessmentHashSet = new HashSet<>();
        Car car = new Car(
                dto.getId(),
                dto.getBrand(),
                dto.getModel(),
                dto.getPower(),
                dto.getYear(),
                carAssessmentHashSet);
        if (dto.getAssessmentDtos() != null) {
            for (AssessmentDto assessmentDto : dto.getAssessmentDtos()) {
                CarAssessment carAssessment = new CarAssessment();
                carAssessment.setId(assessmentDto.getId());
                carAssessment.setValue(assessmentDto.getValue());
                carAssessment.setDate(assessmentDto.getDate());
                carAssessment.setCar(car);
                carAssessmentHashSet.add(carAssessment);
            }
        }

        return car;
    }

    @Override
    public CarDto toDTO(Car car) {

        Set<AssessmentDto> assessmentDtos = car.getCarAssessments().stream()
                .map(carAssessment -> {
                    AssessmentDto assessmentDto = new AssessmentDto();
                    assessmentDto.setId(carAssessment.getId());
                    assessmentDto.setValue(carAssessment.getValue());
                    assessmentDto.setDate(carAssessment.getDate());
                    return assessmentDto;
                })
                .sorted(Comparator.comparing(AssessmentDto::getDate).reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return new CarDto(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getPower(),
                car.getYear(),
                assessmentDtos
        );
    }

    @Override
    public Long getId(Car car) {
        return car.getId();
    }
}
