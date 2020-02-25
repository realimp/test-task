package com.mcb.creditfactory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ASSESSED_VALUES")
public class AssessedValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "assessed_value")
    private BigDecimal value;

    @Column(name = "assessed_date")
    private Date date;

    @Column(name = "collateral_object_id")
    private Long collateralObjectId;
}
