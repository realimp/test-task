package com.mcb.creditfactory.service.assessedvalue;

import com.mcb.creditfactory.dto.AssessmentDto;
import com.mcb.creditfactory.model.AssessedValue;

import java.math.BigDecimal;

public interface AssessedValueService {
    AssessedValue save(BigDecimal value);

    AssessedValue save(AssessmentDto object);
}
