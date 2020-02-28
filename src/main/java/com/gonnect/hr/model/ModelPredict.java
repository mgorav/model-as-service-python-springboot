package com.gonnect.hr.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ModelPredict {
    private double salary;

    public ModelPredict(double salary) {
        this.salary = salary;
    }
}
