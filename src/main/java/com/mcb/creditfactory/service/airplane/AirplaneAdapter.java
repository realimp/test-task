package com.mcb.creditfactory.service.airplane;

import com.mcb.creditfactory.dto.AirplaneDto;
import com.mcb.creditfactory.external.CollateralObject;
import com.mcb.creditfactory.external.CollateralType;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
public class AirplaneAdapter implements CollateralObject {
    private AirplaneDto airplane;

    @Override
    public BigDecimal getValue() {
        return airplane.getValue();
    }

    @Override
    public Short getYear() {
        return airplane.getYear();
    }

    @Override
    public LocalDate getDate() {
        return LocalDate.now();
    }

    @Override
    public CollateralType getType() {
        return CollateralType.AIRPLANE;
    }
}
