package com.mcb.creditfactory.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AIRPLANE")
public class Airplane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private String manufacturer;

    @Column(name = "year_of_issue")
    private Short year;

    private Integer fuelCapacity;
    private Integer seats;

    @OneToMany
    @JoinColumn(name = "collateral_object_id")
    private List<AssessedValue> assessedValues;
}
