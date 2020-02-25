package com.mcb.creditfactory.service.assessedvalue;

import com.mcb.creditfactory.dto.AssessmentDto;
import com.mcb.creditfactory.model.AssessedValue;
import com.mcb.creditfactory.repository.AssessedValuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;

@Service
public class AssessedValueServiceImpl implements AssessedValueService {
    @Autowired
    private AssessedValuesRepository assessedValuesRepository;

    @Override
    public AssessedValue save(BigDecimal value) {
        AssessedValue assessedValue = new AssessedValue();
        assessedValue.setDate(new Date(System.currentTimeMillis()));
        assessedValue.setValue(value);
        return assessedValuesRepository.save(assessedValue);
    }

    @Override
    public AssessedValue save(AssessmentDto object) {
        AssessedValue assessedValue = new AssessedValue();
        assessedValue.setDate(new Date(object.getDate().getTime()));
        assessedValue.setValue(object.getValue());
        assessedValue.setCollateralObjectId(object.getCollateralId());
        return assessedValuesRepository.save(assessedValue);
    }
}
