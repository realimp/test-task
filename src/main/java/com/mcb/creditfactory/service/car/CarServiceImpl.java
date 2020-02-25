package com.mcb.creditfactory.service.car;

import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.external.ExternalApproveService;
import com.mcb.creditfactory.model.AssessedValue;
import com.mcb.creditfactory.model.Car;
import com.mcb.creditfactory.repository.CarRepository;
import com.mcb.creditfactory.service.assessedvalue.AssessedValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private ExternalApproveService approveService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private AssessedValueService assessedValueService;

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
        return carRepository.findById(id);
    }

    @Override
    public Car fromDto(CarDto dto) {
        List<AssessedValue> assessedValues = new ArrayList<>();
        assessedValues.add(assessedValueService.save(dto.getValue()));
        return new Car(
                dto.getId(),
                dto.getBrand(),
                dto.getModel(),
                dto.getPower(),
                dto.getYear(),
                assessedValues
        );
    }

    @Override
    public CarDto toDTO(Car car) {
        List<AssessedValue> assessedValues = car.getAssessedValues();

        AssessedValue latestAssessedValue = assessedValues
                .stream()
                .sorted(Comparator.comparing(AssessedValue::getDate).reversed())
                .findFirst().get();

        return new CarDto(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getPower(),
                car.getYear(),
                latestAssessedValue.getValue()
        );
    }

    @Override
    public Long getId(Car car) {
        return car.getId();
    }
}
