package com.gonnect.hr.model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.pmml4s.model.Model;
@SpringBootApplication
public class HrModelApplication {

    public static void main(String[] args) {

        Model model = Model.fromFile("/Users/gmalho/Documents/Personal/MyGitHub/hr-model/src/main/python/RandomForestRegressor.pmml");

        System.out.println(model);

        SpringApplication.run(HrModelApplication.class, args);
    }

}
