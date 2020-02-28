package com.gonnect.hr.model;

import net.razorvine.pyro.NameServerProxy;
import net.razorvine.pyro.PyroProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class HrModelApplication {

    public static void main(String[] args) throws IOException {
//
//        Model model = Model.fromFile("/Users/gmalho/Documents/Personal/MyGitHub/hr-model/src/main/python/RandomForestRegressor.pmml");
//
//        var outcome = model.predict(new Double[]{6.5});
//
//        System.out.println(outcome);
//
//        System.out.println(model);


//        NameServerProxy ns = NameServerProxy.locateNS(null);
//        PyroProxy remoteobject = new PyroProxy(ns.lookup("NabeeModel"));
//
//
//        Object output = remoteobject.call("predict",new Double[] {6.5});

        SpringApplication.run(HrModelApplication.class, args);
    }

}
