package com.mcb.creditfactory.service;

import com.mcb.creditfactory.dto.AirplaneDto;
import com.mcb.creditfactory.dto.AssessmentDto;
import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.dto.Collateral;
import com.mcb.creditfactory.model.AssessedValue;
import com.mcb.creditfactory.service.airplane.AirplaneService;
import com.mcb.creditfactory.service.assessedvalue.AssessedValueService;
import com.mcb.creditfactory.service.car.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CollateralService {
    @Autowired
    private CarService carService;

    @Autowired
    private AirplaneService airplaneService;

    @Autowired
    private AssessedValueService assessedValueService;

    @SuppressWarnings("ConstantConditions")
    public Long saveCollateral(Collateral object) {
        if (object instanceof CarDto) {
            CarDto car = (CarDto) object;
            boolean approved = carService.approve(car);
            if (!approved) {
                return null;
            }

            return Optional.of(car)
                    .map(carService::fromDto)
                    .map(carService::save)
                    .map(carService::getId)
                    .orElse(null);
        }

        if (object instanceof AirplaneDto) {
            AirplaneDto airplane = (AirplaneDto) object;
            boolean approved = airplaneService.approve(airplane);
            if (!approved) {
                return null;
            }

            return Optional.of(airplane)
                    .map(airplaneService::fromDto)
                    .map(airplaneService::save)
                    .map(airplaneService::getId)
                    .orElse(null);
        }

        throw new IllegalArgumentException();
    }

    public Collateral getInfo(Collateral object) {
        if (object instanceof CarDto) {
            return Optional.of((CarDto) object)
                    .map(carService::fromDto)
                    .map(carService::getId)
                    .flatMap(carService::load)
                    .map(carService::toDTO)
                    .orElse(null);
        }

        if (object instanceof AirplaneDto) {
            return Optional.of((AirplaneDto) object)
                    .map(airplaneService::fromDto)
                    .map(airplaneService::getId)
                    .flatMap(airplaneService::load)
                    .map(airplaneService::toDTO)
                    .orElse(null);
        }

        throw new IllegalArgumentException();
    }

    public AssessedValue assessCollateral(AssessmentDto object) {
        Long objectId = object.getCollateralId();
        if (carService.load(objectId).isPresent() || airplaneService.load(objectId).isPresent()) {
            return assessedValueService.save(object);
        }

        throw new IllegalArgumentException();
    }
}
