package com.gonnect.hr.model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.pmml4s.model.Model;
import net.razorvine.pyro.*;

import java.io.IOException;

@SpringBootApplication
public class HrModelApplication {

    public static void main(String[] args) throws IOException {

        Model model = Model.fromFile("/Users/gmalho/Documents/Personal/MyGitHub/hr-model/src/main/python/RandomForestRegressor.pmml");

        var outcome = model.predict(new Double[]{6.5});

        System.out.println(outcome);

        System.out.println(model);


        NameServerProxy ns = NameServerProxy.locateNS(null);
        PyroProxy remoteobject = new PyroProxy(ns.lookup("NabeeModel"));


        Object output = remoteobject.call("predict",new Double[] {6.5});

        SpringApplication.run(HrModelApplication.class, args);
    }

}
