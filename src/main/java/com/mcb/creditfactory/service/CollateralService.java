package com.mcb.creditfactory.service;

import com.mcb.creditfactory.dto.AirplaneDto;
import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.dto.Collateral;
import com.mcb.creditfactory.external.CollateralType;
import com.mcb.creditfactory.service.airplane.AirplaneService;
import com.mcb.creditfactory.service.car.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

// TODO: reimplement this
@Service
public class CollateralService {
    @Autowired
    private CarService carService;

    @Autowired
    private AirplaneService airplaneService;

    @SuppressWarnings("ConstantConditions")
    public Long saveCollateral(Collateral object) {
        boolean approved;
        switch (object.getType()) {
            case CAR:
                CarDto car = (CarDto) object;
                approved = carService.approve(car);
                if (!approved) {
                    return null;
                }
                return Optional.of(car)
                        .map(carService::fromDto)
                        .map(carService::save)
                        .map(carService::getId)
                        .orElse(null);
            case AIRPLANE:
                AirplaneDto airplaneDto = (AirplaneDto) object;
                approved = airplaneService.approve(airplaneDto);
                if (!approved) {
                    return null;
                }
                return Optional.of(airplaneDto)
                        .map(airplaneService::fromDto)
                        .map(airplaneService::save)
                        .map(airplaneService::getId)
                        .orElse(null);
            default:
                throw new IllegalArgumentException();
        }
    }

    public Collateral getInfo(Collateral object) {

        switch (object.getType()) {
            case CAR:
                return Optional.of((CarDto) object)
                        .map(carService::fromDto)
                        .map(carService::getId)
                        .flatMap(carService::load)
                        .map(carService::toDTO)
                        .orElse(null);
            case AIRPLANE:
                return Optional.of((AirplaneDto) object)
                        .map(airplaneService::fromDto)
                        .map(airplaneService::getId)
                        .flatMap(airplaneService::load)
                        .map(airplaneService::toDTO)
                        .orElse(null);
            default:
                throw new IllegalArgumentException();
        }

    }
}
