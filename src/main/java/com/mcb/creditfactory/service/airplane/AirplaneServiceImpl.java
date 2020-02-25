package com.mcb.creditfactory.service.airplane;

import com.mcb.creditfactory.dto.AirplaneDto;
import com.mcb.creditfactory.external.ExternalApproveService;
import com.mcb.creditfactory.model.Airplane;
import com.mcb.creditfactory.model.AssessedValue;
import com.mcb.creditfactory.repository.AirplaneRepository;
import com.mcb.creditfactory.service.assessedvalue.AssessedValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AirplaneServiceImpl implements AirplaneService {
    @Autowired
    private ExternalApproveService approveService;

    @Autowired
    private AirplaneRepository airplaneRepository;

    @Autowired
    private AssessedValueService assessedValueService;

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
        return airplaneRepository.findById(id);
    }

    @Override
    public Airplane fromDto(AirplaneDto dto) {
        List<AssessedValue> assessedValues = new ArrayList<>();
        assessedValues.add(assessedValueService.save(dto.getValue()));
        return new Airplane(
                dto.getId(),
                dto.getBrand(),
                dto.getModel(),
                dto.getManufacturer(),
                dto.getYear(),
                dto.getFuelCapacity(),
                dto.getSeats(),
                assessedValues
        );
    }

    @Override
    public AirplaneDto toDTO(Airplane airplane) {
        List<AssessedValue> assessedValues = airplane.getAssessedValues();

        AssessedValue latestAssessedValue = assessedValues
                .stream()
                .sorted(Comparator.comparing(AssessedValue::getDate).reversed())
                .findFirst().get();

        return new AirplaneDto(
                airplane.getId(),
                airplane.getBrand(),
                airplane.getModel(),
                airplane.getManufacturer(),
                airplane.getYear(),
                airplane.getFuelCapacity(),
                airplane.getSeats(),
                latestAssessedValue.getValue()
        );
    }

    @Override
    public Long getId(Airplane airplane) {
        return airplane.getId();
    }
}
